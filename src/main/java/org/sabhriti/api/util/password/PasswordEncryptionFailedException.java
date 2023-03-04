package org.sabhriti.api.util.password;

public class PasswordEncryptionFailedException extends Exception {
    public PasswordEncryptionFailedException(Throwable previous) {
        super("Failed to encrypt password due to %s".formatted(previous.getMessage()));
    }
}
