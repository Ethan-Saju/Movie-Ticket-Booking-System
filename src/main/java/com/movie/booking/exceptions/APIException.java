package com.movie.booking.exceptions;

public class APIException extends RuntimeException {
    public APIException(String message) {
        super(message);
    }
}
