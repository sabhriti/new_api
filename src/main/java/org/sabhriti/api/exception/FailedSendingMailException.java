package org.sabhriti.api.exception;

public class FailedSendingMailException extends Exception {
    public FailedSendingMailException(String message) {
        super(message);
    }
}
