package io.b3.Controllers;

import io.b3.Models.*;
import io.b3.Repositories.ProductRepository;
import io.b3.Services.ImageService;
import io.b3.Services.ProductService;
import io.b3.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        product.setStatus("OnGoing");

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

        int prod_len = products.size();
        Random rn = new Random();

        // Create the hero product (you can modify the logic to select which product is the hero)
        Product heroProduct = products.isEmpty() ? null : products.get(rn.nextInt(prod_len)); // Example logic for hero product


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

    @Autowired
    private UserService userService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable String id) {
        Optional<Product> productOptional = productService.getProductById(id);

        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product product = productOptional.get();

        // Build the product response with constant alt_text
        Map<String, Object> response = new HashMap<>();

        // Product details
        Map<String, Object> productDetails = new HashMap<>();
        productDetails.put("id", product.getId());
        productDetails.put("name", product.getName());
        productDetails.put("description", product.getDescription());
        productDetails.put("base_price", product.getBasePrice());
        productDetails.put("max_price", product.getMaxPrice());  // 50% more than base price
        productDetails.put("current_price", product.getHighestBid());  // Highest bid is the current price

        // Image list with constant alt_text
        List<Map<String, String>> images = new ArrayList<>();
        for (String url : product.getImageUrls()) {
            Map<String, String> image = new HashMap<>();
            image.put("url", url);
            image.put("alt_text", "Product Image");  // Constant alt text
            images.add(image);
        }
        productDetails.put("images", images);
        productDetails.put("status", product.getStatus());

        // Seller details (assuming the product's userId matches the seller's ID)
        Optional<User> sellerOptional = userService.getUserById(product.getUserId());
        if (sellerOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User seller = sellerOptional.get();

        Map<String, Object> sellerDetails = new HashMap<>();
        sellerDetails.put("id", seller.getId());
        sellerDetails.put("name", seller.getUsername());
        sellerDetails.put("contact", seller.getContact());

        // Combine product and seller details into the final response
        response.put("product", productDetails);
        response.put("seller", sellerDetails);

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{productId}/{sellerId}/accept")
    public ResponseEntity<String> acceptProductSale(
            @PathVariable String productId,
            @PathVariable String sellerId) {

        Optional<Product> productOptional = productService.getProductById(productId);

        if (productOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        Product product = productOptional.get();

        // Verify if the seller is authorized
        if (!product.getUserId().equals(sellerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Seller not authorized.");
        }

        // Update the product status to "Over"
        product.setStatus("Over");
        productService.saveProduct(product);  // Save the updated product

        return ResponseEntity.ok("Product sale marked as 'Over'.");
    }
}
