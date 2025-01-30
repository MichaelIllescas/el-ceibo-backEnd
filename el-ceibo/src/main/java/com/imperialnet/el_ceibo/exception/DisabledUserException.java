package com.imperialnet.el_ceibo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class DisabledUserException extends RuntimeException {
    public DisabledUserException(String message) {
        super(message);
    }
}

