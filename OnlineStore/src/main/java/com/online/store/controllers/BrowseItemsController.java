package com.online.store.controllers;

import com.online.store.exceptions.emptyBrowseExceptions;
import com.online.store.exceptions.searchItemException;
import com.online.store.models.Product;
import com.online.store.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Contains the endpoint for allowing a user to browse the items available in the store.
 */
@Controller
public class BrowseItemsController {

    private final Logger LOGGER = LoggerFactory.getLogger(BrowseItemsController.class);

    private ProductService productService;
    
    public BrowseItemsController(ProductService productService) {
    	this.productService = productService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    
    /**
     * This allows a user to view all the products.
     * @return A ResponseEntity with the HttpStatus response.
     */
    @GetMapping("/browse-categories")
    public ResponseEntity< Iterable<String>> browseAllCategories() {
        try {
            Iterable<String> listAllCategories =  productService.listUniqueCategories();
            LOGGER.info("Product browsing successful.");
            return ResponseEntity.ok().body(listAllCategories);
        } catch (Exception ex) {
            LOGGER.error("Product browsing unsuccessful.");
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * This return all the items within the category.
     * @param category
     * @return A ResponseEntity with all category items.
     */
    @GetMapping("/browse-category-items/{category}")
    public ResponseEntity<Iterable<Product>>browseCategoryItems(@PathVariable("category") String category) {
        try {
            Iterable<Product> listAllCategoryItems =  productService.listCategoryItems(category);
            if(ifBrowseExists(category)){
                LOGGER.info("Successful Browse.");
                return ResponseEntity.ok().body(listAllCategoryItems);
            } else {
                LOGGER.info("Unsuccessful Browse.");
                throw new emptyBrowseExceptions();
            }
        } catch (Exception ex) {
            LOGGER.error("Couldn't Browse for your desired product.");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/search-items-by-name/{itemName}")
    public ResponseEntity< Iterable<Product>>searchForItemByName( @PathVariable("itemName") String itemName) {
        try {
            Iterable<Product> listAllSearchItems =  productService.searchForItemByName(itemName);
            if(ifItemNotInDatabase(itemName)){
                LOGGER.info("Successful Search.");
                return ResponseEntity.ok().body(listAllSearchItems);
            } else {
                LOGGER.info("Unsuccessful Search, Try again!.");
                throw new searchItemException();
            }
        } catch (Exception ex) {
            LOGGER.error("Item not in Database.");
            return ResponseEntity.badRequest().build();
        }
    }


    private Boolean ifBrowseExists(String category) {
        Iterable<Product> listAllCategoryItems =  productService.listCategoryItems(category);
        for (Product p : listAllCategoryItems) {
            if(!p.getCategory().equals(category))
                return false;
            }
        return true;
    }

    private Boolean ifItemNotInDatabase(String itemSearch) {
        Iterable<Product> listAllSearchItems =  productService.searchForItemByName(itemSearch);
        for (Product p : listAllSearchItems) {
            if(!p.getName().equals(itemSearch))
                return false;
        }
        return true;
    }
}