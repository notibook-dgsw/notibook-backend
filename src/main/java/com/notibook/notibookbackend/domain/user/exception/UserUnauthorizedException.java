package com.notibook.notibookbackend.domain.user.exception;

import com.notibook.notibookbackend.global.exception.defaults.UnauthorizedException;

public class UserUnauthorizedException extends UnauthorizedException {
    public UserUnauthorizedException() {
        super("로그인할 수 없습니다.");
    }
}
