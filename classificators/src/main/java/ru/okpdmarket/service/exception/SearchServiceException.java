package ru.okpdmarket.service.exception;

import java.io.IOException;


public class SearchServiceException extends RuntimeException {

    public SearchServiceException(String s, IOException e) {
        super(s, e);
    }

    public SearchServiceException(Exception ie) {
        super(ie);
    }
}
