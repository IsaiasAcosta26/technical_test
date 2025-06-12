package com.oriontek.prueba.tecnica.mapper;

import com.oriontek.prueba.tecnica.domain.model.Customer;
import com.oriontek.prueba.tecnica.dto.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(CustomerDto customerDto);
    CustomerDto toDto(Customer customer);
}
