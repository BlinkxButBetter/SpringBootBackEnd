package io.b3.Controllers;

import io.b3.Models.Bid;
import io.b3.Models.ProductResponse;
import io.b3.Repositories.ProductRepository;
import io.b3.Services.ImageService;
import io.b3.Models.Product;
import io.b3.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductRepository productRepository;

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

    @GetMapping("/serve")
    public ResponseEntity<ProductResponse> serveProducts() {
        // Fetch products from the repository
        List<Product> products = productRepository.findAll();

        // Create the hero product (you can modify the logic to select which product is the hero)
        Product heroProduct = products.isEmpty() ? null : products.get(0); // Example logic for hero product

        // Create the ProductResponse object
        ProductResponse response = new ProductResponse();

        // Set hero information
        if (heroProduct != null) {
            ProductResponse.Hero hero = new ProductResponse.Hero();
            hero.setId(heroProduct.getId());
            hero.setName(heroProduct.getName());
            hero.setDesc(heroProduct.getDescription());
            hero.setCurPrice(heroProduct.getBasePrice());
            hero.setPreview(heroProduct.getImageUrls());
            response.setHero(hero);
        }

        // Organize products into categories for the scroll area
        Map<String, List<ProductResponse.ProductDTO>> scrollArea = new HashMap<>();
        for (Product product : products) {
            ProductResponse.ProductDTO productDTO = new ProductResponse.ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setDesc(product.getDescription());
            productDTO.setCurPrice(product.getBasePrice());
            productDTO.setPreview(product.getImageUrls());

            // Categorize the products (you can modify the categories as needed)
            String category = product.getCategory();
            scrollArea.putIfAbsent(category, new java.util.ArrayList<>());
            scrollArea.get(category).add(productDTO);
        }

        response.setScrollArea(scrollArea);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{productId}/bid")
    public ResponseEntity<Bid> placeBid(@PathVariable String productId, @RequestBody Bid bid) {
        Bid savedBid = productService.placeBid(productId, bid);
        return ResponseEntity.ok(savedBid);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
