package com.notibook.notibookbackend.global.exception.defaults;

import com.notibook.notibookbackend.global.exception.BusinessException;

public class InvalidInputException extends BusinessException {
    public InvalidInputException(String message) {
        super(400, message);
    }
}
