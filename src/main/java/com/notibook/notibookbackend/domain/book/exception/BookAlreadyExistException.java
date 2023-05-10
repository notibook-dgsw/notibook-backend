package com.notibook.notibookbackend.domain.book.exception;

import com.notibook.notibookbackend.global.exception.BusinessException;

public class BookAlreadyExistException extends BusinessException {
    public BookAlreadyExistException() {
        super(409, "Book already exists.");
    }
}
