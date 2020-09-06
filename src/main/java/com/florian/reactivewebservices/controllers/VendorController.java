package com.florian.reactivewebservices.controllers;

import com.florian.reactivewebservices.domain.Vendor;
import com.florian.reactivewebservices.repositories.VendorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping({"/api/v1/vendors",VendorController.BASE_URL})
public class VendorController {
    public static final String BASE_URL = "/api/v1/vendors/";

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Vendor> findById(@PathVariable String id) {
        return vendorRepository.findById(id);

    }
}
