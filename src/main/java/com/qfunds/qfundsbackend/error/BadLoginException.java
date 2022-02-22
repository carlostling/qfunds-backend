package com.qfunds.qfundsbackend.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadLoginException extends RuntimeException {

    public BadLoginException() {
        super("Bad login. Username or password is null or empty");
    }

}