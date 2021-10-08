package hr.fer.evaluator.service;

import hr.fer.evaluator.domain.Product;
import hr.fer.evaluator.rest.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> listAll();

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO fetchProduct(Long id);

    List<ProductDTO> listProductsWithProductType(Long id);
}
