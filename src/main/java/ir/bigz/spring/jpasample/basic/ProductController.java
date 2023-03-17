package ir.bigz.spring.jpasample.basic;

import org.springframework.http.ResponseEntity;
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
