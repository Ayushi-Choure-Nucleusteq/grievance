package com.grievance.exception;

/**
 * Error Response Class.
 */
public class ErrorResponse {

    /** Containing error message. */
	private String message;

	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param amessage
	 */
	public void setMessage(final String amessage) {
		this.message = amessage;
	}

	/**
	 * Constructor.
	 *
	 * @param amessage
	 */
	public ErrorResponse(final String amessage) {
		super();
		this.message = amessage;
	}



}
