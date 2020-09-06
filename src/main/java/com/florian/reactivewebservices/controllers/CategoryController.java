package com.florian.reactivewebservices.controllers;

import com.florian.reactivewebservices.domain.Category;
import com.florian.reactivewebservices.repositories.CategoryRepository;
import com.sun.xml.internal.ws.policy.sourcemodel.ModelNode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = {"/api/v1/categories", CategoryController.BASE_URL})
public class CategoryController {
    private final CategoryRepository categoryRepository;
    public static final String BASE_URL = "/api/v1/categories/";

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Category> getById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }
}
