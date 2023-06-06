package com.notibook.notibookbackend.domain.book.presentation;

import com.notibook.notibookbackend.domain.book.exception.BookAlreadyExistException;
import com.notibook.notibookbackend.domain.book.repository.BookRepository;
import com.notibook.notibookbackend.domain.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/book-registration")
@RequiredArgsConstructor
@RestController
public class BookAdminController {
    private final BookService bookService;
    private final BookRepository bookRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{book-isbn}")
    public void registerBook(@PathVariable("book-isbn") String isbn) {
        if(bookRepository.findById(isbn).isPresent())
            throw new BookAlreadyExistException();

        bookService.loadNewBook(isbn);
    }
}
