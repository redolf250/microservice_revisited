package org.itech.redolf.repository;

import org.itech.redolf.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    void deleteProductByName(String productName);
}
