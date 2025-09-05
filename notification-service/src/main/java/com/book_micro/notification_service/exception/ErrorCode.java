package com.book_micro.notification_service.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // Authentication errors
    UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "You do not have permission", HttpStatus.FORBIDDEN),

    // invalid request errors
    INVALID_KEY(2001, "Invalid key", HttpStatus.BAD_REQUEST),

    // Email
    CANNOT_SEND_EMAIL(3001, "Cannot send email", HttpStatus.BAD_REQUEST),
    // General errors
    UNKNOWN_ERROR(9999, "An unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

    private int code;
    private HttpStatus statusCode;
    private String message;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
