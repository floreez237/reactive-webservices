package com.florian.reactivewebservices.repositories;

import com.florian.reactivewebservices.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor,String> {
}
