package com.florian.reactivewebservices.controllers;

import com.florian.reactivewebservices.domain.Vendor;
import com.florian.reactivewebservices.repositories.VendorRepository;
import de.flapdoodle.embed.mongo.config.MongodProcessOutputConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class VendorControllerTest {

    WebTestClient webTestClient;
    @Mock
    VendorRepository vendorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        webTestClient = WebTestClient.bindToController(new VendorController(vendorRepository))
                .build();
    }

    @Test
    void findAll() {
        when(vendorRepository.findAll()).thenReturn(Flux.just(Vendor.builder().id("qwe").firstName("florian").build(),
                Vendor.builder().id("dwa").lastName("Lowe").build()));
        webTestClient.get()
                .uri(VendorController.BASE_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void findById() {
        final Vendor vendor = Vendor.builder().id("dda").build();
        when(vendorRepository.findById(anyString())).thenReturn(Mono.just(vendor));
        webTestClient.get()
                .uri(VendorController.BASE_URL + "sdsd")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Vendor.class)
                .isEqualTo(vendor);
    }
}