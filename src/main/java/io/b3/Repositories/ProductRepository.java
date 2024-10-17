package io.b3.Repositories;// ProductRepository.java
import io.b3.Models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByIdIn(List<String> ids);
}
