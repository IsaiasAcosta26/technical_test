package com.oriontek.prueba.tecnica.testProcessor;

import com.oriontek.prueba.tecnica.application.Repository.CustomerRepository;
import com.oriontek.prueba.tecnica.application.dto.CustomerDTO;
import com.oriontek.prueba.tecnica.application.mapper.CustomerMapper;
import com.oriontek.prueba.tecnica.application.processor.CustomerProcessor;
import com.oriontek.prueba.tecnica.domain.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerProcessorTest {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    private CustomerProcessor customerProcessor;

    private final Customer customer = new Customer();
    private final CustomerDTO customerDTO = new CustomerDTO();

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerMapper = mock(CustomerMapper.class);
        customerProcessor = new CustomerProcessor(customerRepository, customerMapper);

        customer.setId(1L);
        customer.setName("Juan Pérez");
        customerDTO.setId(1L);
        customerDTO.setName("Juan Pérez");
    }

    @Test
    void testCreate() {
        when(customerMapper.toEntity(customerDTO)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toDto(customer)).thenReturn(customerDTO);

        CustomerDTO result = customerProcessor.create(customerDTO);

        assertEquals("Juan Pérez", result.getName());
        verify(customerRepository).save(customer);
    }

    @Test
    void testGetById_found() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerMapper.toDto(customer)).thenReturn(customerDTO);

        CustomerDTO result = customerProcessor.getById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testGetById_notFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> customerProcessor.getById(1L));

        assertTrue(exception.getMessage().contains("No encontrado"));
    }

    @Test
    void testGetAll() {
        List<Customer> customerList = Arrays.asList(customer);
        when(customerRepository.findAll()).thenReturn(customerList);
        when(customerMapper.toDto(customer)).thenReturn(customerDTO);

        List<CustomerDTO> result = customerProcessor.getAll();

        assertEquals(1, result.size());
        assertEquals("Juan Pérez", result.get(0).getName());
    }

    @Test
    void testUpdate_found() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerMapper.toEntity(customerDTO)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toDto(customer)).thenReturn(customerDTO);

        CustomerDTO result = customerProcessor.update(1L, customerDTO);

        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdate_notFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> customerProcessor.update(1L, customerDTO));

        assertTrue(exception.getMessage().contains("No encontrado"));
    }

    @Test
    void testDelete() {
        customerProcessor.delete(1L);
        verify(customerRepository).deleteById(1L);
    }
}
