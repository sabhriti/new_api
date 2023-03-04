package org.sabhriti.api.util.password;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordEncryption {

    public static final String ALGORITHM_SHA_512 = "SHA-512";

    @Value("${security.password.encrypt.algorithm:SHA-512}")
    private String encryptionAlgorithm;

    public String encrypt(String rawPassword) throws PasswordEncryptionFailedException {
        var messageDigest = this.createMessageDigest();

        var hash = messageDigest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder(new BigInteger(1, hash).toString(16));

        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    private MessageDigest createMessageDigest() throws PasswordEncryptionFailedException {
        try {
            return MessageDigest.getInstance(this.encryptionAlgorithm);
        } catch (NoSuchAlgorithmException exception) {
            try {
                return MessageDigest.getInstance(ALGORITHM_SHA_512);
            } catch (NoSuchAlgorithmException e) {
                throw new PasswordEncryptionFailedException(e);
            }
        }
    }
}
