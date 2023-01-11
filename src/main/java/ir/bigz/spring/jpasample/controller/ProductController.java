package ir.bigz.spring.jpasample.controller;

import ir.bigz.spring.jpasample.dao.ProductDao;
import ir.bigz.spring.jpasample.entity.Product;
import ir.bigz.spring.jpasample.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getProduct() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        Product save = productService.save(product);
        return ResponseEntity.ok("The product created");
    }
}
