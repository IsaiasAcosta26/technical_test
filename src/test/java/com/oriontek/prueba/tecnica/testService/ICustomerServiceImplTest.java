package com.oriontek.prueba.tecnica.testService;

import com.oriontek.prueba.tecnica.application.Repository.CustomerRepository;
import com.oriontek.prueba.tecnica.application.dto.CustomerDTO;
import com.oriontek.prueba.tecnica.application.mapper.CustomerMapper;
import com.oriontek.prueba.tecnica.application.service.impl.ICustomerServiceImpl;
import com.oriontek.prueba.tecnica.domain.model.Customer;
import com.oriontek.prueba.tecnica.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ICustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private ICustomerServiceImpl customerService;

    private Customer customer;
    private CustomerDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setId(1L);
        customer.setName("Juan Pérez");
        customer.setEmail("juan.perez@example.com");
        customer.setPhone("555-1234");

        dto = new CustomerDTO();
        dto.setName("Carlos Gómez");
        dto.setEmail("carlos.gomez@example.com");
        dto.setPhone("555-5678");
    }

    @Test
    void update_WhenCustomerExists_ShouldUpdateAndReturnDTO() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toDto(customer)).thenReturn(dto);

        CustomerDTO result = customerService.update(1L, dto);

        verify(customerRepository).findById(1L);
        verify(customerRepository).save(customer);
        verify(customerMapper).toDto(customer);

        assertThat(result).isEqualTo(dto);
        assertThat(customer.getName()).isEqualTo("Carlos Gómez");
        assertThat(customer.getEmail()).isEqualTo("carlos.gomez@example.com");
        assertThat(customer.getPhone()).isEqualTo("555-5678");
    }

    @Test
    void update_WhenCustomerDoesNotExist_ShouldThrowException() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.update(99L, dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Customer not found with ID: 99");

        verify(customerRepository).findById(99L);
        verifyNoMoreInteractions(customerRepository);
        verifyNoInteractions(customerMapper);
    }
}
