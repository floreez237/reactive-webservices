package com.florian.reactivewebservices.controllers;

import com.florian.reactivewebservices.domain.Category;
import com.florian.reactivewebservices.repositories.CategoryRepository;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest
class CategoryControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    CategoryRepository categoryRepository;

    @Test
    void getAllCategories() {
        when(categoryRepository.findAll()).thenReturn(Flux.just(new Category("ewwe", "asa"), new Category("ewswe", "asa")));
        webTestClient.get()
                .uri(CategoryController.BASE_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$", hasSize(2)).exists()
                .jsonPath("$.[0].id").isEqualTo("ewwe");

    }

    @Test
    void getById() {
        when(categoryRepository.findById(anyString())).thenReturn(Mono.just(new Category("ere", "dsds")));

        webTestClient.get()
                .uri(CategoryController.BASE_URL + "ssa")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo("ere");
    }
}