package com.oriontek.prueba.tecnica.application.processor;

import com.oriontek.prueba.tecnica.application.Repository.AddressRepository;
import com.oriontek.prueba.tecnica.application.dto.AddressDTO;
import com.oriontek.prueba.tecnica.application.mapper.AddressMapper;
import com.oriontek.prueba.tecnica.domain.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressProcessor extends BaseProcessor<Address, AddressDTO> {
    public AddressProcessor(AddressRepository repository, AddressMapper mapper) {
        super(repository, mapper);
    }
}