package com.florian.reactivewebservices.controllers;

import com.florian.reactivewebservices.domain.Category;
import com.florian.reactivewebservices.repositories.CategoryRepository;
import com.sun.xml.internal.ws.policy.sourcemodel.ModelNode;
import org.reactivestreams.Publisher;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createCategory(@RequestBody Mono<Category> category) {
        return categoryRepository.saveAll(category).then();
    }
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Category> updateCategory(@RequestBody Mono<Category> category, @PathVariable String id) {
        return category.flatMap(category1 -> {
            category1.setId(id);
            return categoryRepository.save(category1);
        });
    }

    @PatchMapping("{id}")
    public Mono<Category> patchCategory(@RequestBody Category sentCategory, @PathVariable String id) {
        Mono<Category> patchedCategory = categoryRepository.findById(id)
                .map(category -> {
                    if (sentCategory.getDescription() != null) {
                        category.setDescription(sentCategory.getDescription());
                    }
                    return category;
                });
        return patchedCategory.flatMap(categoryRepository::save);
    }
}
