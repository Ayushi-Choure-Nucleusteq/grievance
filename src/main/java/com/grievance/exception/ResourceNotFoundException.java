package com.grievance.exception;

/**
 * Custom exception indicating that a resource was not found.
 */
public class ResourceNotFoundException extends RuntimeException {

    /** Serial version UID for serialization. */
    private static final long serialVersionUID = -3091712233922099666L;

	/**
     * New resource not found exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
