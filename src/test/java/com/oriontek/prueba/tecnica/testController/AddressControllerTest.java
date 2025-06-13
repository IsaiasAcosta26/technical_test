package com.oriontek.prueba.tecnica.testController;

import com.oriontek.prueba.tecnica.application.dto.AddressDTO;
import com.oriontek.prueba.tecnica.application.processor.AddressProcessor;
import com.oriontek.prueba.tecnica.controller.AddressController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private AddressProcessor processor;

    @InjectMocks
    private AddressController controller;

    private AddressDTO addressDTO;
    private final Long customerId = 1L;
    private final Long addressId = 1L;

    @BeforeEach
    void setUp() {
        addressDTO = new AddressDTO();
        addressDTO.setId(addressId);
        addressDTO.setStreet("Main Street");
        addressDTO.setCity("New York");
        addressDTO.setCountry("USA");
        addressDTO.setCustomerId(customerId);
    }

    @Test
    void getById_ShouldReturnAddressWhenExists() {
        // Arrange
        when(processor.getById(addressId)).thenReturn(addressDTO);

        // Act
        ResponseEntity<AddressDTO> response = controller.getById(addressId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(addressDTO, response.getBody());

        verify(processor, times(1)).getById(addressId);
    }

    @Test
    void getAll_ShouldReturnListOfAddresses() {
        // Arrange
        List<AddressDTO> addresses = Arrays.asList(addressDTO, new AddressDTO());
        when(processor.getAll()).thenReturn(addresses);

        // Act
        ResponseEntity<List<AddressDTO>> response = controller.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().contains(addressDTO));

        verify(processor, times(1)).getAll();
    }

    @Test
    void update_ShouldReturnUpdatedAddress() {
        // Arrange
        AddressDTO updatedDTO = new AddressDTO();
        updatedDTO.setStreet("Updated Street");
        when(processor.update(eq(addressId), any(AddressDTO.class))).thenReturn(updatedDTO);

        // Act
        ResponseEntity<AddressDTO> response = controller.update(addressId, addressDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Street", response.getBody().getStreet());

        verify(processor, times(1)).update(eq(addressId), any(AddressDTO.class));
    }

    @Test
    void delete_ShouldReturnNoContent() {
        // Arrange
        doNothing().when(processor).delete(addressId);

        // Act
        ResponseEntity<Void> response = controller.delete(addressId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(processor, times(1)).delete(addressId);
    }
}