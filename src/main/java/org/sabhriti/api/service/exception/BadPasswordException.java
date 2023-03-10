package org.sabhriti.api.service.exception;

public class BadPasswordException extends Exception{
    public BadPasswordException(String message) {
        super(message);
    }
}
