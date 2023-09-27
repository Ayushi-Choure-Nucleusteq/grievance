package com.grievance.serviceimpl;

import java.util.Base64;

import org.springframework.stereotype.Component;

/**
 * Utility class for decoding passwords.
 */
@Component
public class DecodePassword {

     /**
     * Decodes a Base64 encoded password.
     *
     * @param encodedPassword the encoded password
     * @return the decoded password
     * @throws IllegalArgumentException if the encoded password is invalid
     */
    final String decodePassword(final String encodedPassword) {
        try {
            return new String(Base64.getDecoder().decode(encodedPassword));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid password");
        }
    }
}
