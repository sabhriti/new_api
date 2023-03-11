package org.sabhriti.api.exception;

public class BadPasswordException extends Exception{
    public BadPasswordException(String message) {
        super(message);
    }
}
