package com.notibook.notibookbackend.global.exception.defaults;

import com.notibook.notibookbackend.global.exception.BusinessException;

public class NotFoundException extends BusinessException {
    public NotFoundException(String message) {
        super(404, message);
    }
}
