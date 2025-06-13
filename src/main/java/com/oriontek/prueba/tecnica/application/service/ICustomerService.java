package com.oriontek.prueba.tecnica.application.service;

import com.oriontek.prueba.tecnica.application.dto.CustomerDTO;
import java.util.List;

public interface ICustomerService {

    CustomerDTO create(CustomerDTO customerDTO);

    CustomerDTO getById(Long id);

    List<CustomerDTO> getAll();

    CustomerDTO update(Long id, CustomerDTO customerDTO);

    void delete(Long id);
}
