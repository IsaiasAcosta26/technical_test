package com.oriontek.prueba.tecnica.application.service;

import com.oriontek.prueba.tecnica.application.dto.AddressDTO;
import java.util.List;

public interface IAddressService {

    AddressDTO create(Long customerId, AddressDTO addressDTO);

    AddressDTO getById(Long id);

    List<AddressDTO> getAll();

    AddressDTO update(Long id, AddressDTO addressDTO);

    void delete(Long id);
}
