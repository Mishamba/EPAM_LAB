package com.epam.esm.controller.json.entity;

import org.springframework.http.HttpStatus;

public class JsonError extends JsonAnswer {
    private int errorCode;

    public JsonError(HttpStatus status, String message, int errorCode) {
        super(status, message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
