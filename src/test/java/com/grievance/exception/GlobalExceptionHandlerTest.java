package com.grievance.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    public void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleResourceNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");

        ResponseEntity<ErrorResponse> result = exceptionHandler.handleResourceNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Resource not found", result.getBody().getMessage());
    }

    @Test
    public void testHandleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Illegal argument");

        ResponseEntity<ErrorResponse> result = exceptionHandler.handleIllegalArgumentException(ex);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("Illegal argument", result.getBody().getMessage());
    }


    @Test
    public void testHandleRecordAlreadyExistException() {
        RecordAlreadyExistException ex = new RecordAlreadyExistException("Record already exists");

        ResponseEntity<ErrorResponse> result = exceptionHandler.handleRecordAlreadyExistException(ex);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("Record already exists", result.getBody().getMessage());
    }

    @Test
    public void testHandleUnauthorizedException() {
        UnauthorizedException ex = new UnauthorizedException("Unauthorized access");

        ResponseEntity<ErrorResponse> result = exceptionHandler.handleUnauthorizedException(ex);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("Unauthorized access", result.getBody().getMessage());
    }

    @Test
    public void testHandleCannotEditTicketException() {
        CannotEditTicketException ex = new CannotEditTicketException("Cannot edit ticket");

        ResponseEntity<ErrorResponse> result = exceptionHandler.handleCannotEditTicketException(ex);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("Cannot edit ticket", result.getBody().getMessage());
    }

}
