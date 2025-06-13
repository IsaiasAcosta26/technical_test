package com.oriontek.prueba.tecnica.testController;

import com.oriontek.prueba.tecnica.application.dto.CustomerDTO;
import com.oriontek.prueba.tecnica.application.processor.CustomerProcessor;
import com.oriontek.prueba.tecnica.controller.CustomerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    private CustomerProcessor processor;
    private CustomerController controller;

    private final CustomerDTO customerDTO = new CustomerDTO();

    @BeforeEach
    void setUp() {
        processor = mock(CustomerProcessor.class);
        controller = new CustomerController(processor);

        customerDTO.setId(1L);
        customerDTO.setName("Juan Pérez");
    }

    @Test
    void testCreate() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(processor.create(any(CustomerDTO.class))).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> response = controller.create(customerDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customerDTO, response.getBody());
        assertNotNull(response.getHeaders().getLocation());
        verify(processor).create(customerDTO);
    }

    @Test
    void testGetById() {
        when(processor.getById(1L)).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> response = controller.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerDTO, response.getBody());
        verify(processor).getById(1L);
    }

    @Test
    void testGetAll() {
        List<CustomerDTO> list = Arrays.asList(customerDTO);
        when(processor.getAll()).thenReturn(list);

        ResponseEntity<List<CustomerDTO>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(processor).getAll();
    }

    @Test
    void testUpdate() {
        when(processor.update(eq(1L), any(CustomerDTO.class))).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> response = controller.update(1L, customerDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerDTO, response.getBody());
        verify(processor).update(1L, customerDTO);
    }

    @Test
    void testDelete() {
        doNothing().when(processor).delete(1L);

        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(processor).delete(1L);
    }
}
