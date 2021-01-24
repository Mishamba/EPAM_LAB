package com.epam.esm.controller.json.entity;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public class JsonAnswer {
    private HttpStatus status;
    private String message;

    public JsonAnswer(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JsonAnswer answer = (JsonAnswer) o;
        return status.equals(answer.status) &&
                message.equals(answer.message);
    }

    @Override
    public int hashCode() {
        int prime = 36;
        int hash = status.hashCode() * prime;
        hash *= message.hashCode() * prime;
        return hash;
    }
}
