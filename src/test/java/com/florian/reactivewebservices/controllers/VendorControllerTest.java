package com.florian.reactivewebservices.controllers;

import com.florian.reactivewebservices.domain.Category;
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
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void createVendor() {
        Vendor vendor = Vendor.builder().firstName("florian").build();
        Vendor savedvendor = Vendor.builder().id("dd").firstName("florian").build();
        when(vendorRepository.save(any())).thenReturn(Mono.just(savedvendor));

        webTestClient.post()
                .uri(VendorController.BASE_URL)
                .body(BodyInserters.fromValue(vendor))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.firstName").isEqualTo("florian");
    }

    @Test
    void updateCategory() {
        Vendor vendor = Vendor.builder().firstName("florian").build();
        Vendor savedVendor = Vendor.builder().id("123").firstName("florian").build();
        when(vendorRepository.save(any())).thenReturn(Mono.just(savedVendor));

        webTestClient.put()
                .uri(VendorController.BASE_URL + "123")
                .body(BodyInserters.fromValue(vendor))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Vendor.class)
                .isEqualTo(savedVendor);

    }

    @Test
    void patchCategory() {
        Vendor vendor = Vendor.builder().id("123").build();
        Vendor patchedVendor = Vendor.builder().id("123").firstName("florian").build();
        when(vendorRepository.save(any())).thenReturn(Mono.just(patchedVendor));
        when(vendorRepository.findById(anyString())).thenReturn(Mono.just(patchedVendor));
        webTestClient.patch()
                .uri(VendorController.BASE_URL + "123")
                .body(Mono.just(vendor),Vendor.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Vendor.class)
                .isEqualTo(patchedVendor);

    }
}