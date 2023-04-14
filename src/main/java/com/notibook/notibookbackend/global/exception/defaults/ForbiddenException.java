package com.notibook.notibookbackend.global.exception.defaults;

import com.notibook.notibookbackend.global.exception.BusinessException;

public class ForbiddenException extends BusinessException {
    public ForbiddenException(String message) {
        super(403, message);
    }
}
