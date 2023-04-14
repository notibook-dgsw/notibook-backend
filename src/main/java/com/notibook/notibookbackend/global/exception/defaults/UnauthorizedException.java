package com.notibook.notibookbackend.global.exception.defaults;

import com.notibook.notibookbackend.global.exception.BusinessException;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(String message) {
        super(401, message);
    }
}
