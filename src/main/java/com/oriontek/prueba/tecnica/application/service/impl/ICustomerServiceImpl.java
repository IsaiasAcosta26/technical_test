package com.oriontek.prueba.tecnica.application.service.impl;

import com.oriontek.prueba.tecnica.application.Repository.CustomerRepository;
import com.oriontek.prueba.tecnica.application.dto.CustomerDTO;
import com.oriontek.prueba.tecnica.application.mapper.CustomerMapper;
import com.oriontek.prueba.tecnica.application.service.ICustomerService;
import com.oriontek.prueba.tecnica.domain.model.Customer;
import com.oriontek.prueba.tecnica.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ICustomerServiceImpl extends BaseService<Customer, CustomerDTO> implements ICustomerService {

    public ICustomerServiceImpl(CustomerRepository repository, CustomerMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public CustomerDTO update(Long id, CustomerDTO dto) {
        CustomerRepository customerRepository = (CustomerRepository) repository;
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());

        return mapper.toDto(customerRepository.save(existing));
    }
}