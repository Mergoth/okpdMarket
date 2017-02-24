package ru.okpdmarket.service.exception;

/**
 * Created by vladislav on 15/01/2017.
 */
public class ClassificatorNotFoundException extends RuntimeException {
    public ClassificatorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClassificatorNotFoundException(String message) {
        super(message);
    }
}
