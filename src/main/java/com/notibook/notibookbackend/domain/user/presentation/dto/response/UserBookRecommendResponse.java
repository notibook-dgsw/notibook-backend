package com.notibook.notibookbackend.domain.user.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserBookRecommendResponse {
    private List<String> books;
}
