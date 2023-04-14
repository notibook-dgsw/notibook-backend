package com.notibook.notibookbackend.domain.user.presentation.dto.response;

import com.notibook.notibookbackend.domain.book.presentation.dto.response.BookSimpleResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserPageResponse {
    private String name;
    private String loginId;
    private List<BookSimpleResponse> books;
}
