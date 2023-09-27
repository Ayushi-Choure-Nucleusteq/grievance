package com.grievance.exception;

/**
 * An exception that is thrown.
 */
public class UnauthorizedException extends RuntimeException {

    /**Serial version UID for serialization.*/
    private static final long serialVersionUID = -6092163197830155984L;

    /**
     * Constructs a new exception with lack of authorization.
     *
     * @param message the detail message.
     */
    public UnauthorizedException(final String message) {
        super(message);
    }
}
