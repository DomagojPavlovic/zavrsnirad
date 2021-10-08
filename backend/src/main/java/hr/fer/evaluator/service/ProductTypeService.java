package hr.fer.evaluator.service;

import hr.fer.evaluator.rest.dto.ProductTypeDTO;

import java.util.List;

public interface ProductTypeService {

    List<ProductTypeDTO> listAll();

    ProductTypeDTO createProductType(ProductTypeDTO productTypeDTO);

    ProductTypeDTO fetchProductType(Long id);
}
