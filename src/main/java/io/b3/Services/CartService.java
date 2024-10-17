package io.b3.Services;// CartService.java
import io.b3.Models.Cart;
import io.b3.Models.Product;
import io.b3.Repositories.CartRepository;
import io.b3.Repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public List<Product> getCartProducts(String userId) {
        // Fetch the cart for the given userId
        Optional<Cart> cart = cartRepository.findByUserId(userId);

        if (cart.isPresent()) {
            // Retrieve product details using product IDs from the cart
            List<String> productIds = cart.get().getProductIds();
            return productRepository.findByIdIn(productIds);
        }
        return List.of(); // Return empty list if cart not found
    }

    public void addProductToCart(String userId, String productId) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);

        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
            List<String> productIds = cart.getProductIds();

            // Add product ID if it's not already in the list
            if (!productIds.contains(productId)) {
                productIds.add(productId);
                cart.setProductIds(productIds);
            }
        } else {
            // Create a new cart if it doesn't exist
            cart = new Cart();
            cart.setUserId(userId);
            cart.setProductIds(List.of(productId));
        }

        // Save the updated or new cart
        cartRepository.save(cart);
    }
}
