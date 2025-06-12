package com.oriontek.prueba.tecnica.controller;

import com.oriontek.prueba.tecnica.application.dto.AddressDTO;
import com.oriontek.prueba.tecnica.application.processor.AddressProcessor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressProcessor processor;

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<AddressDTO> create(@PathVariable Long customerId, @Valid @RequestBody AddressDTO address) {
        AddressDTO created = processor.createForCustomer(customerId, address);
        return ResponseEntity.created(URI.create("/api/addresses/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(processor.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAll() {
        return ResponseEntity.ok(processor.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable Long id, @Valid @RequestBody AddressDTO address) {
        return ResponseEntity.ok(processor.update(id, address));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        processor.delete(id);
        return ResponseEntity.noContent().build();
    }
}