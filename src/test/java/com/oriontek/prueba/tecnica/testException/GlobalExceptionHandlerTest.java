package com.oriontek.prueba.tecnica.testException;

import com.oriontek.prueba.tecnica.exception.GlobalExceptionHandler;
import com.oriontek.prueba.tecnica.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private WebRequest mockRequest;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        mockRequest = mock(WebRequest.class);
        when(mockRequest.getDescription(false)).thenReturn("/api/test");
    }

    @Test
    void testHandleNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Customer not found");
        ResponseEntity<Object> response = handler.handleNotFound(ex, mockRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertNotNull(body);
        assertEquals("Customer not found", body.get("message"));
        assertEquals(404, body.get("status"));
        assertEquals("Not Found", body.get("error"));
        assertEquals("/api/test", body.get("path"));
    }

    @Test
    void testHandleBadRequest() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");
        ResponseEntity<Object> response = handler.handleBadRequest(ex, mockRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertNotNull(body);
        assertEquals("Invalid argument", body.get("message"));
        assertEquals(400, body.get("status"));
        assertEquals("Bad Request", body.get("error"));
        assertEquals("/api/test", body.get("path"));
    }

    @Test
    void testHandleGeneralException() {
        Exception ex = new Exception("Unexpected");
        ResponseEntity<Object> response = handler.handleGeneralException(ex, mockRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertNotNull(body);
        assertEquals("Unexpected error occurred", body.get("message"));
        assertEquals(500, body.get("status"));
        assertEquals("Internal Server Error", body.get("error"));
        assertEquals("/api/test", body.get("path"));
    }

    @Test
    void testHandleValidationErrors() {
        // Simular errores de validación
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("customerDTO", "email", "must be valid");
        FieldError fieldError2 = new FieldError("customerDTO", "name", "must not be empty");

        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Object> response = handler.handleValidationErrors(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertNotNull(body);
        assertEquals("must be valid", body.get("email"));
        assertEquals("must not be empty", body.get("name"));
    }
}
