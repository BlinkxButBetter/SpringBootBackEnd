package io.b3.Controllers;

import io.b3.Models.ImageService;
import io.b3.Models.Product;
import io.b3.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<Product> uploadProduct(
            @RequestParam("userId") String userId,
            @RequestParam("productName") String productName,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("basePrice") double basePrice,
            @RequestParam("files") MultipartFile[] files) throws IOException {

        // Prepare product object
        Product product = new Product();
        product.setUserId(userId);
        product.setProductName(productName);
        product.setDescription(description);
        product.setCategory(category);
        product.setBasePrice(basePrice);

        // Upload images and store their URLs
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String imageUrl = imageService.storeImage(file); // Assume storeImage returns URL
            imageUrls.add(imageUrl);
        }
        product.setImageUrls(imageUrls);

        // Save the product
        Product savedProduct = productService.uploadProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
