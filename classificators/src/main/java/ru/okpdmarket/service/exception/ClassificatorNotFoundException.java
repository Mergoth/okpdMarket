package ru.okpdmarket.service.exception;


public class ClassificatorNotFoundException extends RuntimeException {
    public ClassificatorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClassificatorNotFoundException(String message) {
        super(message);
    }
}
