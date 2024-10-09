package io.b3.Services;

import io.b3.Models.Product;
import io.b3.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product uploadProduct(Product product) {
        return productRepository.save(product); // Save the product in the database
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll(); // Fetch all products
    }
}
