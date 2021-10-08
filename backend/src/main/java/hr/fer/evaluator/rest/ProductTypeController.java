package hr.fer.evaluator.rest;

import hr.fer.evaluator.rest.dto.ProductTypeDTO;
import hr.fer.evaluator.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productTypes")
public class ProductTypeController {

    @Autowired
    ProductTypeService productTypeService;

    @PostMapping
    public ProductTypeDTO createProductType(@RequestBody ProductTypeDTO productTypeDTO){
        return productTypeService.createProductType(productTypeDTO);
    }

    @GetMapping
    public List<ProductTypeDTO> listProductTypes(){
        return productTypeService.listAll();
    }

    @GetMapping("/{id}")
    public ProductTypeDTO getProductType(@PathVariable("id") Long id){
        return productTypeService.fetchProductType(id);
    }
}
