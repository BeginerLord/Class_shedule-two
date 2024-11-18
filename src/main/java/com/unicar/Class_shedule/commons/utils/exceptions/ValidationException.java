package com.unicar.Class_shedule.commons.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No se puede eliminar el docente porque está vinculado a un usuario.")
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}