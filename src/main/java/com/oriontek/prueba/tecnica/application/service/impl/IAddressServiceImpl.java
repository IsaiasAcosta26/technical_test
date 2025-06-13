package com.oriontek.prueba.tecnica.application.service.impl;

import com.oriontek.prueba.tecnica.application.Repository.AddressRepository;
import com.oriontek.prueba.tecnica.application.Repository.CustomerRepository;
import com.oriontek.prueba.tecnica.application.dto.AddressDTO;
import com.oriontek.prueba.tecnica.application.mapper.AddressMapper;
import com.oriontek.prueba.tecnica.application.service.IAddressService;
import com.oriontek.prueba.tecnica.domain.model.Address;
import com.oriontek.prueba.tecnica.domain.model.Customer;
import com.oriontek.prueba.tecnica.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class IAddressServiceImpl extends BaseService<Address, AddressDTO> implements IAddressService {

    private final CustomerRepository customerRepository;
    private final AddressMapper addressMapper;

    public IAddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper, CustomerRepository customerRepository) {
        super(addressRepository, addressMapper);
        this.customerRepository = customerRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public AddressDTO create(Long customerId, AddressDTO addressDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        Address address = addressMapper.toEntity(addressDTO);
        address.setCustomer(customer);
        return mapper.toDto(repository.save(address));
    }

    @Override
    public AddressDTO update(Long id, AddressDTO dto) {
        Address address = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));

        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());
        address.setZipCode(dto.getZipCode());

        return mapper.toDto(repository.save(address));
    }
}
