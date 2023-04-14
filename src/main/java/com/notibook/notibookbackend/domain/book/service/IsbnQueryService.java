package com.notibook.notibookbackend.domain.book.service;

import com.notibook.notibookbackend.domain.book.entity.BookEntity;

import java.util.Optional;

public interface IsbnQueryService {
    Optional<BookEntity> searchBookByIsbn(String isbn);
}
