package com.oriontek.prueba.tecnica.application.service.impl;

import com.oriontek.prueba.tecnica.application.Repository.CustomerRepository;
import com.oriontek.prueba.tecnica.application.dto.CustomerDTO;
import com.oriontek.prueba.tecnica.application.service.CustomerService;
import com.oriontek.prueba.tecnica.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerDTO create(CustomerDTO customer) {
        return customerRepository.save(customer);
    }

    public CustomerDTO getById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
    }

    public List<CustomerDTO> getAll() {
        return customerRepository.findAll();
    }

    public CustomerDTO update(Long id, CustomerDTO updatedCustomer) {
        CustomerDTO existing = getById(id);
        existing.setName(updatedCustomer.getName());
        return customerRepository.save(existing);
    }

    public void delete(Long id) {
        CustomerDTO existing = getById(id);
        customerRepository.delete(existing);
    }
}
