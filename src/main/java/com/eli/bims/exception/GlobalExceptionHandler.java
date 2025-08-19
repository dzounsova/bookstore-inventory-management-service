package com.eli.bims.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ErrorResponse handleBookNotFoundException(final BookNotFoundException ex) {
        return handleException(ex, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        return handleException(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGenericException(Exception ex) {
        return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    private ErrorResponse handleException(final Exception ex, final HttpStatus status, final String message) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, status, message).build();
        log.error("Error: {}", errorResponse);
        return errorResponse;
    }
}
