package com.online.store.services;

import com.online.store.models.Product;
import com.online.store.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImplementation implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * This gets all the unique categories stored in the database.
     * @return The Iterable list of all the unique categories in the database.
     */
    @Override
    public Iterable<String> listUniqueCategories() {
        List<String> allUniqueCategories = new ArrayList<>();
        Iterable<Product> allCategories = productRepository.findAll();

        allCategories.forEach((element)->{
            if(!allUniqueCategories.contains(element.getCategory())) {
                allUniqueCategories.add(element.getCategory());
            }
        });
        return allUniqueCategories;
    }

    /**
     * This gets all the category Items stored in the database.
     * @param category the specific category type we wish to search for.
     * @return list of all the Items in the category.
     */
    @Override
    public Iterable<Product> listCategoryItems(String category) {
        List<Product> allCategoryItems = new ArrayList<>();
        Iterable<Product> allItems = productRepository.findAll();

        allItems.forEach((item)->{
            if(item.getCategory().equals(category)) {
                allCategoryItems.add(item);
            }
        });
        return allCategoryItems;
    }

    /**
     * This gets all the category Items stored in the database.
     * @param itemSearch of the item you want to search
     * @return list of all the Items in the category.
     */
    @Override
    public Iterable<Product> searchForItemByName(String itemSearch) {
        List<Product> searchItems = new ArrayList<>();
        Iterable<Product> allRelativeSearchItems = productRepository.findAll();
            allRelativeSearchItems.forEach((item)->{
                if(item.getName().equalsIgnoreCase(itemSearch) ||item.getName().toUpperCase().contains(itemSearch) || item.getName().toLowerCase().contains(itemSearch) ){
                    searchItems.add(item);
                }
            });
        return searchItems;
    }
    /**
     * This allows a system admin to add a new product to the database.
     * @param product The Product that the system admin wishes to add to the database.
     * @return The Product that was added to the database, as a form of validation.
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * This allows a system admin to a delete a specific product from the database.
     * @param id The id of the product that the system admin wishes to delete from the database.
     */
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public Object save(Object object) {
        saveProduct((Product)object);

        return object;
    }

    @Override
    public void delete(Integer id) {
        deleteProduct(id);
    }
}
