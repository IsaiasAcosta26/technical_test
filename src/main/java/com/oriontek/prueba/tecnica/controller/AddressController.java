package com.oriontek.prueba.tecnica.controller;

import com.oriontek.prueba.tecnica.application.dto.AddressDTO;
import com.oriontek.prueba.tecnica.application.processor.AddressProcessor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.oriontek.prueba.tecnica.config.ApiConstants.API_VERSION;

@RestController
@RequestMapping(API_VERSION + "/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressProcessor processor;

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<AddressDTO> create(
            @PathVariable Long customerId,
            @Valid @RequestBody AddressDTO addressDTO
    ) {
        addressDTO.setCustomerId(customerId);
        AddressDTO created = processor.create(addressDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getById(@PathVariable Long id) {
        AddressDTO dto = processor.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAll() {
        List<AddressDTO> list = processor.getAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable Long id, @Valid @RequestBody AddressDTO addressDTO) {
        AddressDTO updated = processor.update(id, addressDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        processor.delete(id);
        return ResponseEntity.noContent().build();
    }
}