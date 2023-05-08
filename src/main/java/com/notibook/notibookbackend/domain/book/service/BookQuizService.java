package com.notibook.notibookbackend.domain.book.service;

import com.notibook.notibookbackend.domain.book.presentation.dto.response.BookQuizResponse;

public interface BookQuizService {
    BookQuizResponse generateQuiz(String title);
}
