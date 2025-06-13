package com.oriontek.prueba.tecnica.testProcessor;

import com.oriontek.prueba.tecnica.application.Repository.AddressRepository;
import com.oriontek.prueba.tecnica.application.dto.AddressDTO;
import com.oriontek.prueba.tecnica.application.mapper.AddressMapper;
import com.oriontek.prueba.tecnica.application.processor.AddressProcessor;
import com.oriontek.prueba.tecnica.domain.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressProcessorTest {

    private AddressRepository addressRepository;
    private AddressMapper addressMapper;
    private AddressProcessor addressProcessor;

    private final Address address = new Address();
    private final AddressDTO addressDTO = new AddressDTO();

    @BeforeEach
    void setUp() {
        addressRepository = mock(AddressRepository.class);
        addressMapper = mock(AddressMapper.class);
        addressProcessor = new AddressProcessor(addressRepository, addressMapper);

        // Datos básicos
        address.setId(1L);
        address.setCity("City");
        addressDTO.setId(1L);
        addressDTO.setCity("City");
    }

    @Test
    void testCreate() {
        when(addressMapper.toEntity(addressDTO)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(addressMapper.toDto(address)).thenReturn(addressDTO);

        AddressDTO result = addressProcessor.create(addressDTO);

        assertEquals(addressDTO.getCity(), result.getCity());
        verify(addressRepository).save(address);
    }

    @Test
    void testGetById_found() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(addressMapper.toDto(address)).thenReturn(addressDTO);

        AddressDTO result = addressProcessor.getById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testGetById_notFound() {
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> addressProcessor.getById(1L));

        assertTrue(exception.getMessage().contains("No encontrado"));
    }

    @Test
    void testGetAll() {
        List<Address> addressList = Arrays.asList(address);
        List<AddressDTO> dtoList = Arrays.asList(addressDTO);

        when(addressRepository.findAll()).thenReturn(addressList);
        when(addressMapper.toDto(address)).thenReturn(addressDTO);

        List<AddressDTO> result = addressProcessor.getAll();

        assertEquals(1, result.size());
        assertEquals("City", result.get(0).getCity());
    }

    @Test
    void testUpdate_found() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(addressMapper.toEntity(addressDTO)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(addressMapper.toDto(address)).thenReturn(addressDTO);

        AddressDTO result = addressProcessor.update(1L, addressDTO);

        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdate_notFound() {
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> addressProcessor.update(1L, addressDTO));

        assertTrue(exception.getMessage().contains("No encontrado"));
    }

    @Test
    void testDelete() {
        addressProcessor.delete(1L);
        verify(addressRepository).deleteById(1L);
    }
}
