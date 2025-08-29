package com.book_micro.profile_service.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USERNAME_ALREADY_EXISTS(1001, "Username already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1002, "User not found", HttpStatus.NOT_FOUND),
    DATABASE_ERROR(1003, "Database error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_CREDENTIALS(1004, "Invalid credentials provided", HttpStatus.UNAUTHORIZED),

    // Authentication errors
    USERNAME_NOT_FOUND(1005, "Username not found", HttpStatus.NOT_FOUND),
    USERNAME_OR_PASSWORD_NOT_MATCH(1006, "Username or password does not match", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "You do not have permission", HttpStatus.FORBIDDEN),

    // invalid request errors
    INVALID_KEY(2001, "Invalid key", HttpStatus.BAD_REQUEST),
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
