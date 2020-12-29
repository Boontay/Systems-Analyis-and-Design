package com.online.store.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.store.Constants;
import com.online.store.exceptions.CartIsEmptyException;
import com.online.store.exceptions.ItemNotInCartException;
import com.online.store.exceptions.ItemOutOfStockException;
import com.online.store.models.Product;
import com.online.store.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

/**
 * Contains the endpoint for allowing a user to browse the items available in the store.
 */
@Controller
public class AddItemsToCartController extends com.online.store.controllers.Controller {

    private final Logger LOGGER = LoggerFactory.getLogger(AddItemsToCartController.class);

    @Autowired
    public void setProductService(ProductService productService) {
        super.setService(productService);
    }

    /**
     * This allows a user to add items to their.
     * @param string An input provided by the body of the request.
     * @return A ResponseEntity with the HttpStatus response.
     */
    @PostMapping(Constants.addItemsToCartEndpoint)
    public ResponseEntity<String> addItemsToCart(@RequestBody String string) {
        try {
            /*
            Parsing the inputted String to a Product object.
             */
            Product product = new ObjectMapper().readValue(string, Product.class);

            // todo add in stuff to add items to cart here.
            if (product.getQuantityInStock() > 0) {
                JavaFxController.user.addCartItem(product);
                LOGGER.info("Item is in stock and has been added to cart.");
            } else {
                throw new ItemOutOfStockException();
            }

            return ResponseEntity.ok(string);
        } catch (ItemOutOfStockException | JsonProcessingException ex) {
            LOGGER.error("Item cannot be added to cart. It is out of stock.");
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/remove-items-from-cart")
    public ResponseEntity<String> removeItemFromCart(@RequestBody String string) {
        try {

            Product product = new ObjectMapper().readValue(string, Product.class);

            if (JavaFxController.user.isItemInCart(product)) {
                JavaFxController.user.removeCartItem(product);
            } else {
                throw new ItemNotInCartException();
            }

            LOGGER.info("Item removal from cart successful.");
            return ResponseEntity.ok(string);
        } catch (ItemNotInCartException ex) {
            LOGGER.error("Item removal from cart unsuccessful. Item is not in cart.");
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            LOGGER.error("Failed to remove item from cart.");
            return ResponseEntity.badRequest().build();
        }
    }

    public BigDecimal getCartPrice() {
        try {
            BigDecimal total = JavaFxController.user.getTotalCartPrice();

            LOGGER.info("Total returned.");
            return total;
        } catch (Exception ex) {
            LOGGER.error("Total NOT returned.");
            return new BigDecimal(-1);
        }
    }

    public Product getCartItem(int ind) {
        try {

            Product prod = new Product();

            if (ind >= JavaFxController.user.getCartSize()) {
                prod = JavaFxController.user.getCartItem(ind);
            } else {
                throw new IndexOutOfBoundsException();
            }

            if (prod == null) {
                throw new CartIsEmptyException();
            }

            LOGGER.info("Cart index is in bounds and cart is not empty.");
            return prod;
        } catch (IndexOutOfBoundsException ex) {
            LOGGER.error("The cart does not contain that many items.");
            return null;
        } catch (CartIsEmptyException ex) {
            LOGGER.error("Cart is empty.");
            return null;
        }
    }
}
