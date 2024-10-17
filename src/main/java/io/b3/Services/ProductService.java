package io.b3.Services;

import io.b3.Models.Bid;
import io.b3.Models.Product;
import io.b3.Models.User;
import io.b3.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BidService bidService;

    @Autowired
    private UserService userService;

    public Product uploadProduct(Product product) {
        return productRepository.save(product); // Save the product in the database
    }

    public Bid placeBid(String productId, Bid bid) {
        // Save the bid
        Bid savedBid = bidService.placeBid(bid);

        // Update the product's highest bid if this bid is higher
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        if (savedBid.getBidAmount() > product.getHighestBid()) {
            product.setHighestBid(savedBid.getBidAmount());
            productRepository.save(product);
        }

        return savedBid;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll(); // Fetch all products
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Optional<User> getUserById(String userId) {
        return userService.getUserById(userId);  // Assuming UserService provides this method
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}
