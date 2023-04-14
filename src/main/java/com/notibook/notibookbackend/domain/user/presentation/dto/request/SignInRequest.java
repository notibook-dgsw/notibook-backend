package com.notibook.notibookbackend.domain.user.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class SignInRequest {
    @NotNull
    @NotEmpty
    private String loginId;

    @NotNull
    @NotEmpty
    private String password;
}
