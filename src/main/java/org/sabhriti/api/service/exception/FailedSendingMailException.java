package org.sabhriti.api.service.exception;

public class FailedSendingMailException extends Exception {
    public FailedSendingMailException(String message) {
        super(message);
    }
}
