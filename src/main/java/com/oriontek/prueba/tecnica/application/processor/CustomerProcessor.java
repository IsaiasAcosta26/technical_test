package com.oriontek.prueba.tecnica.application.processor;

import com.oriontek.prueba.tecnica.application.dto.CustomerDTO;
import com.oriontek.prueba.tecnica.application.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerProcessor implements BaseProcessor<CustomerDTO> {

    private final CustomerService service;

    @Override
    public CustomerDTO create(CustomerDTO customer) {
        return service.create(customer);
    }

    @Override
    public CustomerDTO getById(Long id) {
        return service.getById(id);
    }

    @Override
    public List<CustomerDTO> getAll() {
        return service.getAll();
    }

    @Override
    public CustomerDTO update(Long id, CustomerDTO customer) {
        return service.update(id, customer);
    }

    @Override
    public void delete(Long id) {
        service.delete(id);
    }
}
