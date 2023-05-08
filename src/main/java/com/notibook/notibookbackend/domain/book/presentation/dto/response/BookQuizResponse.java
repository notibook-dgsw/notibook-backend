package com.notibook.notibookbackend.domain.book.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookQuizResponse {
    private String question;
    private String answer;
}
