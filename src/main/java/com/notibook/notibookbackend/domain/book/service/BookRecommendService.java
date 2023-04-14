package com.notibook.notibookbackend.domain.book.service;

import com.notibook.notibookbackend.domain.book.entity.BookEntity;

import java.util.List;

public interface BookRecommendService {
    String recommend(List<BookEntity> books);
}
