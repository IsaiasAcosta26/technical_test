package com.oriontek.prueba.tecnica.application.mapper;

import com.oriontek.prueba.tecnica.application.dto.AddressDTO;
import com.oriontek.prueba.tecnica.domain.model.Address;
import com.oriontek.prueba.tecnica.domain.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "customerId", source = "customer.id")
    AddressDTO toDto(Address address);

    @Mapping(target = "customer", source = "customerId", qualifiedByName = "mapCustomer")
    Address toEntity(AddressDTO addressDto);

    @Named("mapCustomer")
    default Customer mapCustomer(Long customerId) {
        if (customerId == null) return null;
        Customer customer = new Customer();
        customer.setId(customerId);
        return customer;
    }
}
