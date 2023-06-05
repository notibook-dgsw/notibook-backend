package com.notibook.notibookbackend.domain.book.exception;

import com.notibook.notibookbackend.global.exception.BusinessException;

public class BookNotFoundException extends BusinessException {
    public BookNotFoundException() {
        super(404, "Book not found.");
    }
}
