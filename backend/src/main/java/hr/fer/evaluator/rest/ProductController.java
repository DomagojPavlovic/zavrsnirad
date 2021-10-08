package hr.fer.evaluator.rest;

import hr.fer.evaluator.rest.dto.ProductDTO;
import hr.fer.evaluator.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO){
       return productService.createProduct(productDTO);
    }

    @GetMapping
    public List<ProductDTO> listProducts(){
        return productService.listAll();
    }

    @GetMapping("/{id}")
    public List<ProductDTO> listProductsWithProductType(@PathVariable("id") Long id){
        return productService.listProductsWithProductType(id);
    }
}
