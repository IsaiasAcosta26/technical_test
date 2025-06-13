package com.oriontek.prueba.tecnica.application.mapper;

import com.oriontek.prueba.tecnica.application.dto.AddressDTO;
import com.oriontek.prueba.tecnica.domain.model.Address;
import com.oriontek.prueba.tecnica.domain.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AddressMapper extends BaseMapper<Address, AddressDTO> {

    @Override
    @Mapping(target = "customer", source = "customerId", qualifiedByName = "mapCustomer")
    Address toEntity(AddressDTO dto);

    @Override
    @Mapping(target = "customerId", source = "customer.id")
    AddressDTO toDto(Address entity);

    @Named("mapCustomer")
    default Customer mapCustomer(Long id) {
        if (id == null) return null;
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }
}
