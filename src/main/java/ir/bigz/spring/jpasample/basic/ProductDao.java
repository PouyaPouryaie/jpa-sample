package ir.bigz.spring.jpasample.basic;

import org.springframework.data.repository.ListCrudRepository;

public interface ProductDao extends ListCrudRepository<Product, Long> {
}
