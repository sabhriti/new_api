package org.sabhriti.api.exception;

public class FailedSendingMailException extends Exception {
    public FailedSendingMailException(String message) {
        super(message);
    }

    public FailedSendingMailException() {
        super("Failed sending password reset mail to provided email address.");
    }
}
