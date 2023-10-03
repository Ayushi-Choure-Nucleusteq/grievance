package com.grievance.outdto;

/**
 * API Response 
 */
public class ApiResponse {

	/* message to be displayed*/
	private String message;
	
	/* message to be displayed*/
	private Boolean success;

	/**
	 * @param amessage
	 * @param asuccess
	 */
	public ApiResponse(String amessage, Boolean asuccess) {
		super();
		this.message = amessage;
		this.success = asuccess;
	}

	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * set message.
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
	/**
	 * @return success
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * set success.
	 * 
	 * @param success
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	

}
