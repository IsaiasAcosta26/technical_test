package com.oriontek.prueba.tecnica.application.mapper;

import com.oriontek.prueba.tecnica.application.dto.AddressDTO;
import com.oriontek.prueba.tecnica.domain.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressDTO addressDto);
    AddressDTO toDto(Address address);
}
