package com.oriontek.prueba.tecnica.application.processor;

import com.oriontek.prueba.tecnica.application.Repository.CustomerRepository;
import com.oriontek.prueba.tecnica.application.dto.CustomerDTO;
import com.oriontek.prueba.tecnica.application.mapper.CustomerMapper;
import com.oriontek.prueba.tecnica.domain.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerProcessor extends BaseProcessor<Customer, CustomerDTO> {
    public CustomerProcessor(CustomerRepository repository, CustomerMapper mapper) {
        super(repository, mapper);
    }
}
