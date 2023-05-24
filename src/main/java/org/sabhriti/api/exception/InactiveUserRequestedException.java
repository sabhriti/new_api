package org.sabhriti.api.exception;

public class InactiveUserRequestedException extends Exception {
    public InactiveUserRequestedException(String theUserIsNotActive) {
        super(theUserIsNotActive);
    }
}
