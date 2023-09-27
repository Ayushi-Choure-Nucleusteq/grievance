package com.grievance.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Global exception handler class to handle application-specific exceptions.
 */
@ControllerAdvice
public final class GlobalExceptionHandler {

    /**
     * Handle resource not found exceptions.
     *
     * @param ex the exception to handle
     * @return the response entity with the error message and HTTP status
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
    		final ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),
        		HttpStatus.NOT_FOUND);
    }

    /**
     * Handle illegal argument exceptions.
     *
     * @param ex the exception to handle
     * @return the response entity with the error message and HTTP status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse>
    handleIllegalArgumentException(
    		final IllegalArgumentException ex) {
        return new ResponseEntity<>(new ErrorResponse(
        		ex.getMessage()),
        		HttpStatus.CONFLICT);
    }

    /**
     * Handle exceptions for records that already exist.
     *
     * @param ex the exception to handle
     * @return the response entity with the error message and HTTP status
     */
    @ExceptionHandler(RecordAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleRecordAlreadyExistException(
            final RecordAlreadyExistException ex) {
     return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),
        		HttpStatus.CONFLICT);
    }

    /**
     * Handle unauthorized exceptions.
     *
     * @param ex the exception to handle
     * @return the response entity with the error message and HTTP status
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
    		final UnauthorizedException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),
        		HttpStatus.CONFLICT);
    }

    /**
     * Handle exceptions related to ticket editing.
     *
     * @param ex the exception to handle
     * @return the response entity with the error message and HTTP status
     */
    @ExceptionHandler(CannotEditTicketException.class)
    public ResponseEntity<ErrorResponse> handleCannotEditTicketException(
            final CannotEditTicketException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),
        		HttpStatus.CONFLICT);
    }

    /**
     * Handle exceptions related to invalid input.
     *
     * @param ex the exception to handle
     * @return the response entity with the error message and HTTP status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>>
    handleMethodArgsNotValidException(
    		final MethodArgumentNotValidException ex) {
		Map<String, String> resp = new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {

			String fieldname = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			resp.put(fieldname, message);
		});
		return new ResponseEntity<Map<String, String>>(
				resp, HttpStatus.BAD_REQUEST);
	}

    /**
     * Handle exceptions related to Constraint violation.
     *
     * @param ex the exception to handle
     * @return the response entity with the error message and HTTP status
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException ex) {
        
        ErrorResponse errorResponse = new ErrorResponse(
                "Invalid input provided.");

        return new ResponseEntity<ErrorResponse>(
                errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle exceptions related to constraint violations.
     *
     * @param ex the exception to handle
     * @return the response entity with the error message and HTTP status
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>>
    handleConstraintViolationException(
            final ConstraintViolationException ex) {
        Map<String, String> resp = new HashMap<String, String>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            resp.put(violation.getPropertyPath()
                    .toString(), violation.getMessage());
        }

        return new ResponseEntity<Map<String, String>>(
                resp, HttpStatus.BAD_REQUEST);
    }


}
