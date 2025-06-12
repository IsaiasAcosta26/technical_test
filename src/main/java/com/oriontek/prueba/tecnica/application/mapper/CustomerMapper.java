package com.oriontek.prueba.tecnica.application.mapper;

import com.oriontek.prueba.tecnica.application.dto.CustomerDTO;
import com.oriontek.prueba.tecnica.domain.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface CustomerMapper {
    Customer toEntity(CustomerDTO customerDto);
    CustomerDTO toDto(Customer customer);

}
