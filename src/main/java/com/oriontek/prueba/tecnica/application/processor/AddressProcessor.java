package com.oriontek.prueba.tecnica.application.processor;

import com.oriontek.prueba.tecnica.application.dto.AddressDTO;
import com.oriontek.prueba.tecnica.application.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddressProcessor {

    private final AddressService service;

    public AddressDTO createForCustomer(Long customerId, AddressDTO address) {
        return service.create(customerId, address);
    }

    public AddressDTO getById(Long id) {
        return service.getById(id);
    }

    public List<AddressDTO> getAll() {
        return service.getAll();
    }

    public AddressDTO update(Long id, AddressDTO address) {
        return service.update(id, address);
    }

    public void delete(Long id) {
        service.delete(id);
    }
}