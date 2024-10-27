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

        Optional<Cart> cart = cartRepository.findByUserId(userId);

        if (cart.isPresent()) {

            List<String> productIds = cart.get().getProductIds();
            return productRepository.findByIdIn(productIds);
        }
        return List.of();
    }

    public void addProductToCart(String userId, String productId) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);

        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
            List<String> productIds = cart.getProductIds();


            if (!productIds.contains(productId)) {
                productIds.add(productId);
                cart.setProductIds(productIds);
            }
        } else {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setProductIds(List.of(productId));
        }

        cartRepository.save(cart);
    }
}
