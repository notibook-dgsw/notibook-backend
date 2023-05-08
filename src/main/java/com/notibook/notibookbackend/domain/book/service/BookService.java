package com.notibook.notibookbackend.domain.book.service;

import com.notibook.notibookbackend.domain.book.entity.*;
import com.notibook.notibookbackend.domain.book.entity.key.UserBookRecordBinder;
import com.notibook.notibookbackend.domain.book.presentation.dto.request.HistoryCreationRequest;
import com.notibook.notibookbackend.domain.book.presentation.dto.request.NoteCreationRequest;
import com.notibook.notibookbackend.domain.book.presentation.dto.request.NoteEditRequest;
import com.notibook.notibookbackend.domain.book.presentation.dto.response.BookDetailResponse;
import com.notibook.notibookbackend.domain.book.presentation.dto.response.BookQuizResponse;
import com.notibook.notibookbackend.domain.book.presentation.dto.response.HistoryResponse;
import com.notibook.notibookbackend.domain.book.presentation.dto.response.NoteResponse;
import com.notibook.notibookbackend.domain.book.repository.BookBindingRepository;
import com.notibook.notibookbackend.domain.book.repository.BookRepository;
import com.notibook.notibookbackend.domain.book.repository.HistoryRecordRepository;
import com.notibook.notibookbackend.domain.book.repository.NoteRepository;
import com.notibook.notibookbackend.domain.user.entity.UserEntity;
import com.notibook.notibookbackend.domain.user.exception.UserUnauthorizedException;
import com.notibook.notibookbackend.domain.user.facade.UserFacade;
import com.notibook.notibookbackend.global.exception.defaults.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BookService {
    private final UserFacade userFacade;
    private final IsbnQueryService isbnQueryService;
    private final BookRepository bookRepository;
    private final BookBindingRepository bookBindingRepository;
    private final HistoryRecordRepository historyRecordRepository;
    private final BookQuizService bookQuizService;
    private final NoteRepository noteRepository;

    private BookEntity loadNewBook(String isbn) {
        return bookRepository.save(
                isbnQueryService.searchBookByIsbn(isbn)
                        .orElseThrow(() -> new NotFoundException("해당하는 책을 조회할 수 없습니다."))
        );
    }

    private UserBookEntity registerUser(BookEntity book, UserEntity user) {
        UserBookEntity binding = UserBookEntity.builder()
                .book(book)
                .user(user)
                .historyRecords(new HashSet<>())
                .notes(new HashSet<>())
                .build();

        UserBookEntity saved = bookBindingRepository.save(binding);
        saved.addRecord(historyRecordRepository.save(
                HistoryEntity.builder()
                        .id(new UserBookRecordBinder(saved, 0))
                        .createdAt(LocalDateTime.now())
                        .build()
                ));

        return saved;
    }

    @Transactional
    public BookDetailResponse bookPage(String isbn) {
        UserEntity user = userFacade.getCurrentUser()
                .orElseThrow(UserUnauthorizedException::new);

        BookEntity book = bookRepository.findById(isbn)
                .orElseGet(() -> loadNewBook(isbn));

        UserBookEntity binding = bookBindingRepository.findByBookAndUser(book, user)
                .orElseGet(() -> registerUser(book, user));


        List<HistoryEntity> history = binding.getHistoryRecords()
                .stream()
                .sorted(Comparator.comparing(HistoryEntity::getCreatedAt))
                .collect(Collectors.toList());

        List<NoteEntity> notes = new ArrayList<>(binding.getNotes()
                .stream()
                .sorted(Comparator.comparing(NoteEntity::getCreatedAt))
                .collect(Collectors.toList()));
        Collections.reverse(notes);

        return BookDetailResponse.builder()
                .isbn(isbn)
                .title(book.getTitle())
                .author(book.getAuthor().split(";")[0].replace("지음", ""))
                .startedAt(history.stream().findFirst()
                        .map(it -> it.getCreatedAt().toLocalDate())
                        .orElse(LocalDate.now()))
                .allPages(book.getPageCount())
                .currentPage(binding.getCurrentPage())
                .notes(notes.stream().map(NoteResponse::fromEntity).collect(Collectors.toList()))
                .history(history.stream().map(HistoryResponse::fromEntity).collect(Collectors.toList()))
                .progress(book.getPageCount() == null ? 0 : (int)(binding.getCurrentPage() / (double)book.getPageCount() * 100))
                .summary(book.getSummary())
                .build();
    }

    @Transactional
    public void addHistory(String isbn, HistoryCreationRequest request) {
        UserEntity user = userFacade.getCurrentUser()
                .orElseThrow(UserUnauthorizedException::new);

        BookEntity book = bookRepository.findById(isbn)
                .orElseThrow(() -> new NotFoundException("No such book"));

        UserBookEntity binding = bookBindingRepository.findByBookAndUser(book, user)
                .orElseThrow(() -> new NotFoundException("No such book"));

        HistoryEntity entity = HistoryEntity.builder()
                .id(new UserBookRecordBinder(binding, request.getPage()))
                .createdAt(LocalDateTime.now())
                .build();

        binding.addRecord(entity);

        historyRecordRepository.save(entity);
    }

    @Transactional
    public void addNote(String isbn, NoteCreationRequest request) {
        UserEntity user = userFacade.getCurrentUser()
                .orElseThrow(UserUnauthorizedException::new);

        BookEntity book = bookRepository.findById(isbn)
                .orElseThrow(() -> new NotFoundException("No such book"));

        UserBookEntity binding = bookBindingRepository.findByBookAndUser(book, user)
                .orElseThrow(() -> new NotFoundException("No such book"));

        NoteEntity entity = NoteEntity.builder()
                .id(new UserBookRecordBinder(binding, request.getPage()))
                .createdAt(LocalDateTime.now())
                .content(request.getContent())
                .build();

        binding.addNote(entity);

        noteRepository.save(entity);
    }

    @Transactional
    public void editNote(String isbn, int page, NoteEditRequest request) {
        UserEntity user = userFacade.getCurrentUser()
                .orElseThrow(UserUnauthorizedException::new);

        BookEntity book = bookRepository.findById(isbn)
                .orElseThrow(() -> new NotFoundException("No such book"));

        UserBookEntity binding = bookBindingRepository.findByBookAndUser(book, user)
                .orElseThrow(() -> new NotFoundException("No such book"));

        NoteEntity note = binding.getNotes().stream()
                .filter(it -> it.getId().getPage().equals(page))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No such note"));

        note.setContent(request.getContent());
    }

    @Transactional
    public void deleteNote(String isbn, int page) {
        UserEntity user = userFacade.getCurrentUser()
                .orElseThrow(UserUnauthorizedException::new);

        BookEntity book = bookRepository.findById(isbn)
                .orElseThrow(() -> new NotFoundException("No such book"));

        UserBookEntity binding = bookBindingRepository.findByBookAndUser(book, user)
                .orElseThrow(() -> new NotFoundException("No such book"));

        NoteEntity note = binding.getNotes().stream()
                .filter(it -> it.getId().getPage().equals(page))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No such note"));

        noteRepository.delete(note);
    }

    public BookQuizResponse getQuiz(String isbn) {
        BookEntity book = bookRepository.findById(isbn)
                .orElseThrow(() -> new NotFoundException("No such book"));

        return bookQuizService.generateQuiz(book.getTitle());
    }
}
