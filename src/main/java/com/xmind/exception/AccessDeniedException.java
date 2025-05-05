package com.xmind.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccessDeniedException extends ResponseStatusException {

    public AccessDeniedException() {
        super(HttpStatus.FORBIDDEN);
    }

    public AccessDeniedException(String reason) {
        super(HttpStatus.FORBIDDEN, reason);
    }

    public AccessDeniedException(String reason, Throwable cause) {
        super(HttpStatus.FORBIDDEN, reason, cause);
    }
}