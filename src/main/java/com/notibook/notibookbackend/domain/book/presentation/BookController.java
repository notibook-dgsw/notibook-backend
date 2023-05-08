package com.notibook.notibookbackend.domain.book.presentation;

import com.notibook.notibookbackend.domain.book.presentation.dto.request.HistoryCreationRequest;
import com.notibook.notibookbackend.domain.book.presentation.dto.request.NoteCreationRequest;
import com.notibook.notibookbackend.domain.book.presentation.dto.request.NoteEditRequest;
import com.notibook.notibookbackend.domain.book.presentation.dto.response.BookDetailResponse;
import com.notibook.notibookbackend.domain.book.presentation.dto.response.BookQuizResponse;
import com.notibook.notibookbackend.domain.book.service.BookService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/books")
@RestController
public class BookController {
    private final BookService bookService;

    @ApiOperation("책 정보 조회")
    @GetMapping("/{isbn}")
    public BookDetailResponse getBookInfo(@PathVariable String isbn) {
        return bookService.bookPage(isbn);
    }

    @ApiOperation("책갈피 추가")
    @PostMapping("/{isbn}/history")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHistory(@PathVariable String isbn, @Validated @RequestBody HistoryCreationRequest request) {
        bookService.addHistory(isbn, request);
    }

    @ApiOperation("독서 퀴즈 가져오기")
    @GetMapping("/{isbn}/quiz")
    public BookQuizResponse getQuiz(@PathVariable String isbn) {
        return bookService.getQuiz(isbn);
    }

    @ApiOperation("독서 노트 추가")
    @PostMapping("/{isbn}/note")
    @ResponseStatus(HttpStatus.CREATED)
    public void editNote(@PathVariable String isbn, @Validated @RequestBody NoteCreationRequest request) {
        bookService.addNote(isbn, request);
    }

    @ApiOperation("독서 노트 수정")
    @PatchMapping("/{isbn}/note/{page}")
    @ResponseStatus(HttpStatus.CREATED)
    public void editNote(
            @PathVariable String isbn,
            @PathVariable int page,
            @Validated @RequestBody NoteEditRequest request) {
        bookService.editNote(isbn, page, request);
    }

    @ApiOperation("독서 노트 삭제")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{isbn}/note/{page}")
    public void deleteNote(
            @PathVariable String isbn,
            @PathVariable int page) {
        bookService.deleteNote(isbn, page);
    }
}
