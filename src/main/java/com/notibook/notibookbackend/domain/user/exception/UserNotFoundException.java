package com.notibook.notibookbackend.domain.user.exception;

import com.notibook.notibookbackend.global.exception.defaults.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("사용자를 찾을 수 없습니다.");
    }
}
