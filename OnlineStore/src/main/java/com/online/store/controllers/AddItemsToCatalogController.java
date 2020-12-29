package com.online.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.store.models.Product;
import com.online.store.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Contains the endpoint for allowing a user to browse the items available in the store.
 */
@Controller
public class AddItemsToCatalogController extends com.online.store.controllers.Controller {

    private final Logger LOGGER = LoggerFactory.getLogger(AddItemsToCartController.class);

    @Autowired
    public void setProductService(ProductService productService) {
        super.setService(productService);
    }

    /**
     * This allows an system admin to add items to a catalog.
     * @param string An input provided by the body of the request.
     * @return A ResponseEntity with the HttpStatus response.
     */
    @PostMapping("/add-items-to-catalog")
    public ResponseEntity<String> addItemsToCatalog(@RequestBody String string) {
        try {
            /*
                Example input, as a JSON input (or at least that's what i've tested it with):
                NOTE: It doesn't matter if "id" is inputted, as it is automatically given an id when it's put in the database.
                {
                    "id": "1254",
                    "productId": "00000",
                    "name": "e",
                    "description": "adadsadsadasdasdasdasdasdsad",
                    "category" : "123",
                    "price": "5.60",
                    "quantityInStock": "0"
                }
             */

            Product product = new ObjectMapper().readValue(string, Product.class);

            super.getService().save(product);

            LOGGER.info("Item addition to catalog successful.");
            return ResponseEntity.ok(string);
        } catch (Exception ex) {
            LOGGER.error("Item addition to catalog unsuccessful.");
            return ResponseEntity.badRequest().build();
        }
    }
}
