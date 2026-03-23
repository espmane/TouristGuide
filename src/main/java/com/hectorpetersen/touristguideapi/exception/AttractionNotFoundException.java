package com.hectorpetersen.touristguideapi.exception;

public class AttractionNotFoundException extends RuntimeException {
    public AttractionNotFoundException(String message) {
        super(message);
    }
}
