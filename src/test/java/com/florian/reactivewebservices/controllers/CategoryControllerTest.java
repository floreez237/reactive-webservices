package com.florian.reactivewebservices.controllers;

import com.florian.reactivewebservices.domain.Category;
import com.florian.reactivewebservices.repositories.CategoryRepository;
import com.sun.corba.se.impl.orbutil.CacheTable;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

//@WebFluxTest
class CategoryControllerTest {


    WebTestClient webTestClient;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        webTestClient = WebTestClient.bindToController(new CategoryController(categoryRepository)).build();
    }

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

    @Test
    void createCategory() {
        Category category = new Category("5540", "fruits");
        when(categoryRepository.saveAll(any(Publisher.class))).thenReturn(Flux.just(category));

        webTestClient.post()
                .uri(CategoryController.BASE_URL)
                .body(BodyInserters.fromValue(category))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated();

    }

    @Test
    void updateCategory() {
        Category category = new Category("5540", "fruits");
        Category savedCategory = new Category("5541", "fruits");
        when(categoryRepository.save(any())).thenReturn(Mono.just(savedCategory));

        webTestClient.put()
                .uri(CategoryController.BASE_URL + "5541")
                .body(BodyInserters.fromValue(category))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Category.class)
                .isEqualTo(savedCategory);

    }


}