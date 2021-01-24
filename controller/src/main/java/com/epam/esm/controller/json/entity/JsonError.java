package com.epam.esm.controller.json.entity;

import org.springframework.http.HttpStatus;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        JsonError error = (JsonError) o;
        return errorCode == error.errorCode &&
                super.equals(o);
    }

    @Override
    public int hashCode() {
        int prime = 3;
        int hash = super.hashCode() * prime;
        hash *= errorCode * prime;
        return hash;
    }
}
