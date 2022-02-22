package com.qfunds.qfundsbackend.config.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtTokenInvalidException extends AuthenticationException {

    public JwtTokenInvalidException(String e) {
        super(e);
    }

}