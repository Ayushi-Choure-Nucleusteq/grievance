package com.grievance.exception;

/**
 * An exception that is thrown when a ticket cannot be edited.
 */
public class CannotEditTicketException extends RuntimeException {

    /** Serial version UID for serialization.*/
    private static final long serialVersionUID = 7389166445213475870L;

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public CannotEditTicketException(final String message) {
        super(message);
    }

}
