package com.online.store.services;

import com.online.store.models.Product;

import java.util.Optional;

public interface ProductService extends Service {

    Iterable<String> listUniqueCategories();

    Iterable<Product> listCategoryItems(String category);

    Iterable<Product> searchForItemByName(String searchItem);

}
