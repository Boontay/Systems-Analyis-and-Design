package com.online.store.repositories;

import com.online.store.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer>  {
}
