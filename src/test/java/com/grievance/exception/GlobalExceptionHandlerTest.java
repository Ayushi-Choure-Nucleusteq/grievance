package com.grievance.exception;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;
//    private WebRequest webRequest;

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
    public void testHandleMethodArgsNotValidException() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "field", "defaultMessage");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(Arrays.asList(fieldError));

        ResponseEntity<Map<String, String>> result = exceptionHandler.handleMethodArgsNotValidException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertTrue(result.getBody().containsKey("field"));
        assertEquals("defaultMessage", result.getBody().get("field"));
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
