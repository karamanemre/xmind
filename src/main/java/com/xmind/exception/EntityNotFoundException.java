package com.xmind.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityNotFoundException extends ResponseStatusException {

    public EntityNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public EntityNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public EntityNotFoundException(String reason, Throwable cause) {
        super(HttpStatus.NOT_FOUND, reason, cause);
    }
}
