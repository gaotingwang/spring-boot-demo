package com.gtw.retry.exception;

public class SelfException extends RuntimeException {
    public SelfException(String message) {
        super(message);
    }
}
