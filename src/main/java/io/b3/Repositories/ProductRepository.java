package io.b3.Repositories;

import io.b3.Models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    // Add custom query methods if needed
}
