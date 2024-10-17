package io.b3.Controllers;// CartController.java
import io.b3.Models.Product;
import io.b3.Services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/get/{userId}")
    public List<Product> getCartProducts(@PathVariable String userId) {
        return cartService.getCartProducts(userId);
    }


    @GetMapping("/add/{userId}/{productId}")
    public ResponseEntity<Map<String, Object>> addProductToCart(
            @PathVariable String userId,
            @PathVariable String productId) {

        cartService.addProductToCart(userId, productId);

        // Prepare JSON response
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Product added to cart successfully.");

        // Return response with HTTP status 200 (OK)
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
