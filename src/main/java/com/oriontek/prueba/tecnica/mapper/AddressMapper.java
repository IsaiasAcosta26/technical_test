package com.oriontek.prueba.tecnica.mapper;

import com.oriontek.prueba.tecnica.domain.model.Address;
import com.oriontek.prueba.tecnica.dto.AddressDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressDto addressDto);
    AddressDto toDto(Address address);
}
