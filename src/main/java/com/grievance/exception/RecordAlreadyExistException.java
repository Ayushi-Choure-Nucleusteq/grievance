package com.grievance.exception;

/**
 * An exception that is thrown when insert a record that already exists.
 */
public class RecordAlreadyExistException extends RuntimeException {

    /** Serial version UID for serialization. */
    private static final long serialVersionUID = -936530325329303993L;

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public RecordAlreadyExistException(final String message) {
        super(message);
    }
}
