package com.example.moslearn.exception;

import java.util.Map;

public class ApiErrorResponse {

    private final String message;
    private final Map<String, String> errors;

    public ApiErrorResponse(String message) {
        this(message, Map.of());
    }

    public ApiErrorResponse(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
