package com.oriontek.prueba.tecnica.testService;

import com.oriontek.prueba.tecnica.application.Repository.AddressRepository;
import com.oriontek.prueba.tecnica.application.Repository.CustomerRepository;
import com.oriontek.prueba.tecnica.application.dto.AddressDTO;
import com.oriontek.prueba.tecnica.application.mapper.AddressMapper;
import com.oriontek.prueba.tecnica.application.service.impl.IAddressServiceImpl;
import com.oriontek.prueba.tecnica.domain.model.Address;
import com.oriontek.prueba.tecnica.domain.model.Customer;
import com.oriontek.prueba.tecnica.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class IAddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private IAddressServiceImpl addressService;

    private AddressDTO dto;
    private Address address;
    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dto = new AddressDTO();
        dto.setStreet("Calle 1");
        dto.setCity("Ciudad");
        dto.setCountry("País");
        dto.setZipCode("12345");

        address = new Address();
        address.setStreet("Calle 1");
        address.setCity("Ciudad");
        address.setCountry("País");
        address.setZipCode("12345");

        customer = new Customer();
        customer.setId(1L);
        customer.setName("Cliente Prueba");
    }

    @Test
    void create_WhenCustomerExists_ShouldCreateAddress() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(addressMapper.toEntity(dto)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(addressMapper.toDto(address)).thenReturn(dto);

        AddressDTO result = addressService.create(1L, dto);

        verify(customerRepository).findById(1L);
        verify(addressMapper).toEntity(dto);
        verify(addressRepository).save(address);
        verify(addressMapper).toDto(address);

        assertThat(result).isEqualTo(dto);
        assertThat(address.getCustomer()).isEqualTo(customer);
    }

    @Test
    void create_WhenCustomerDoesNotExist_ShouldThrowException() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> addressService.create(99L, dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Customer not found with ID: 99");

        verify(customerRepository).findById(99L);
        verifyNoInteractions(addressMapper);
        verifyNoMoreInteractions(addressRepository);
    }

    @Test
    void update_WhenAddressExists_ShouldUpdateFields() {
        Address existing = new Address();
        existing.setId(1L);
        existing.setStreet("Vieja Calle");
        existing.setCity("Vieja Ciudad");
        existing.setCountry("Viejo País");
        existing.setZipCode("00000");

        when(addressRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(addressRepository.save(existing)).thenReturn(address);
        when(addressMapper.toDto(address)).thenReturn(dto);

        AddressDTO result = addressService.update(1L, dto);

        verify(addressRepository).findById(1L);
        verify(addressRepository).save(existing);
        verify(addressMapper).toDto(address);

        assertThat(result).isEqualTo(dto);
        assertThat(existing.getStreet()).isEqualTo("Calle 1");
        assertThat(existing.getCity()).isEqualTo("Ciudad");
        assertThat(existing.getCountry()).isEqualTo("País");
        assertThat(existing.getZipCode()).isEqualTo("12345");
    }

    @Test
    void update_WhenAddressNotFound_ShouldThrowException() {
        when(addressRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> addressService.update(99L, dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Address not found with ID: 99");

        verify(addressRepository).findById(99L);
        verifyNoMoreInteractions(addressMapper);
        verifyNoMoreInteractions(addressRepository);
    }
}