package hr.fer.evaluator.service.impl;

import hr.fer.evaluator.dao.AttributeTypeRepository;
import hr.fer.evaluator.dao.ProductAttributeRepository;
import hr.fer.evaluator.dao.ProductTypeRepository;
import hr.fer.evaluator.domain.ProductAttribute;
import hr.fer.evaluator.domain.ProductType;
import hr.fer.evaluator.rest.dto.ProductTypeDTO;
import hr.fer.evaluator.service.ProductTypeService;
import hr.fer.evaluator.service.exceptions.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductTypeServiceJpa implements ProductTypeService {

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    ProductAttributeRepository productAttributeRepository;

    @Autowired
    AttributeTypeRepository attributeTypeRepository;


    @Override
    public List<ProductTypeDTO> listAll() {

        List<ProductTypeDTO> output = new ArrayList<>();

        List<ProductType> types = productTypeRepository.findAll();
        for(ProductType type : types){
            Long id = type.getProductTypeID();
            List<ProductAttribute> productAttributes = productAttributeRepository.findByProductTypeID(id);
            Long[] values = new Long[productAttributes.size()];
            for(int i = 0 ; i< productAttributes.size();i++){
                values[i] = productAttributes.get(i).getAttributeTypeID();
            }
            output.add(new ProductTypeDTO(id, type.getName(), values));
        }

        return output;
    }

    @Override
    public ProductTypeDTO createProductType(ProductTypeDTO productTypeDTO) {
        Assert.notNull(productTypeDTO, "Product type must not be null.");
        Assert.notNull(productTypeDTO.getName(), "Product name must not be null.");
        Assert.notNull(productTypeDTO.getAttributeIDs(), "Product attribute IDs must not be null.");

        ProductType productType = productTypeRepository.save(new ProductType(productTypeDTO.getName()));
        Long id = productType.getProductTypeID();

        for(Long attributeID : productTypeDTO.getAttributeIDs()){
            if(!attributeTypeRepository.findByAttributeTypeID(attributeID).isPresent())
                throw new RequestDeniedException("Provided attribute with id: " + attributeID + " does not exist.");

            productAttributeRepository.save(new ProductAttribute(attributeID, id));
        }

        productTypeDTO.setId(id);
        return productTypeDTO;
    }

    @Override
    public ProductTypeDTO fetchProductType(Long id) {
        return null;
    }
}
