package ir.bigz.spring.jpasample.dao;

import ir.bigz.spring.jpasample.entity.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductDao extends ListCrudRepository<Product, Long> {
}
