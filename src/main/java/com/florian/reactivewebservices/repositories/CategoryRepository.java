package com.florian.reactivewebservices.repositories;

import com.florian.reactivewebservices.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category,String> {
}
