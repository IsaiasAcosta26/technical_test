package com.oriontek.prueba.tecnica.application.service.impl;

import com.oriontek.prueba.tecnica.application.Repository.AddressRepository;
import com.oriontek.prueba.tecnica.application.Repository.CustomerRepository;
import com.oriontek.prueba.tecnica.application.dto.AddressDTO;
import com.oriontek.prueba.tecnica.application.mapper.AddressMapper;
import com.oriontek.prueba.tecnica.application.service.AddressService;
import com.oriontek.prueba.tecnica.domain.model.Address;
import com.oriontek.prueba.tecnica.domain.model.Customer;
import com.oriontek.prueba.tecnica.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final AddressMapper addressMapper;

    @Override
    public AddressDTO create(Long customerId, AddressDTO addressDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        Address address = addressMapper.toEntity(addressDTO);
        address.setCustomer(customer);

        return addressMapper.toDto(addressRepository.save(address));
    }

    @Override
    public AddressDTO getById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));
        return addressMapper.toDto(address);
    }

    @Override
    public List<AddressDTO> getAll() {
        return addressRepository.findAll().stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO update(Long id, AddressDTO addressDTO) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));

        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setCountry(addressDTO.getCountry());

        return addressMapper.toDto(addressRepository.save(address));
    }

    @Override
    public void delete(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));
        addressRepository.delete(address);
    }
}
