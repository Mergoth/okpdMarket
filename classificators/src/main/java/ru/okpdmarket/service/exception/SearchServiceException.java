package ru.okpdmarket.service.exception;

import java.io.IOException;

/**
 * Created by vladislav on 21/03/2017.
 */
public class SearchServiceException extends RuntimeException {

    public SearchServiceException(String s, IOException e) {
        super(s, e);
    }

    public SearchServiceException(Exception ie) {
        super(ie);
    }
}
