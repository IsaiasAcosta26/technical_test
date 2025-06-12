package com.oriontek.prueba.tecnica.application.service.impl;


import com.oriontek.prueba.tecnica.application.Repository.AddressRepository;
import com.oriontek.prueba.tecnica.application.Repository.CustomerRepository;
import com.oriontek.prueba.tecnica.application.dto.AddressDTO;
import com.oriontek.prueba.tecnica.application.service.AddressService;
import com.oriontek.prueba.tecnica.domain.model.Customer;
import com.oriontek.prueba.tecnica.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public AddressDTO create(Long customerId, AddressDTO address) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        address.setCustomerId(customer);
        return addressRepository.save(address);
    }

    public AddressDTO getById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));
    }

    public List<AddressDTO> getAll() {
        return addressRepository.findAll();
    }

    public AddressDTO update(Long id, AddressDTO updatedAddress) {
        AddressDTO existing = getById(id);
        existing.setStreet(updatedAddress.getStreet());
        existing.setCity(updatedAddress.getCity());
        return addressRepository.save(existing);
    }

    public void delete(Long id) {
        AddressDTO existing = getById(id);
        addressRepository.delete(existing);
    }
}