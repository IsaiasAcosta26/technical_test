package com.oriontek.prueba.tecnica.application.mapper;

import com.oriontek.prueba.tecnica.application.dto.AddressDTO;
import com.oriontek.prueba.tecnica.domain.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.oriontek.prueba.tecnica.domain.model.Customer;

@Mapper(componentModel = "spring")
public interface AddressMapper extends BaseMapper<Address, AddressDTO> {

    @Override
    @Mapping(target = "customer", source = "customerId", qualifiedByName = "mapCustomer")
    Address toEntity(AddressDTO dto);

    @Named("mapCustomer")
    default Customer mapCustomer(Long id) {
        if (id == null) return null;
        Customer c = new Customer();
        c.setId(id);
        return c;
    }
}
