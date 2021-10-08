package hr.fer.evaluator.service.impl;

import hr.fer.evaluator.dao.*;
import hr.fer.evaluator.domain.AttributeDomain;
import hr.fer.evaluator.domain.Product;
import hr.fer.evaluator.domain.ProductAttributeValue;
import hr.fer.evaluator.rest.dto.ProductDTO;
import hr.fer.evaluator.service.ProductService;
import hr.fer.evaluator.service.Util;
import hr.fer.evaluator.service.exceptions.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class ProductServiceJpa implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductAttributeValueRepository productAttributeValueRepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    AttributeTypeRepository attributeTypeRepository;

    @Autowired
    AttributeDomainRepository attributeDomainRepository;

    @Override
    public List<ProductDTO> listAll() {
        return listProducts(repository -> repository.findAll());
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Assert.notNull(productDTO, "Product must not be null.");
        Assert.notNull(productDTO.getProductTypeID(), "Product type ID must not be null.");
        Assert.notNull(productDTO.getName(), "Product name must not be null.");
        Assert.notNull(productDTO.getAttributeValues(), "Product attributes must not be null.");

        if(!productTypeRepository.findByProductTypeID(productDTO.getProductTypeID()).isPresent())
            throw new RequestDeniedException("Provided product with product type ID: " + productDTO.getProductTypeID() + " does not exist.");

        Product product = productRepository.save(new Product(productDTO.getProductTypeID(), productDTO.getName()));
        Long id = product.getProductID();

        List<ProductAttributeValue> toSave = new ArrayList<>();
        for(ProductDTO.LongStringPair attribute: productDTO.getAttributeValues()){
            if(attribute.getValue().equals("")){
                throwExceptionAndDelete("All attribute fields must be filled.", product);
            }

            if(!attributeTypeRepository.findByAttributeTypeID(attribute.getId()).isPresent())
                throwExceptionAndDelete("Provided attribute type with ID: " + attribute.getId() + " does not exist.", product);

            //check if attribute.getValue() is in domain of valid
            List<AttributeDomain> domainValues = attributeDomainRepository.findByAttributeTypeID(attribute.getId());
            if(attributeTypeRepository.findByAttributeTypeID(attribute.getId()).get().getRangeAttribute()){
                if(!Util.isDouble(attribute.getValue())){
                    throwExceptionAndDelete("Provided value for attribute type with name: " + attributeTypeRepository.findByAttributeTypeID(attribute.getId()).get().getName() + " is not in domain.", product);
                }
                Double lowerBound = Double.valueOf(domainValues.get(0).getDomainValue());
                Double upperBound = Double.valueOf(domainValues.get(1).getDomainValue());
                if(Double.valueOf(attribute.getValue())> upperBound || Double.valueOf(attribute.getValue())< lowerBound){
                    throwExceptionAndDelete("Provided value for attribute type with name: " + attributeTypeRepository.findByAttributeTypeID(attribute.getId()).get().getName() + " is not in domain.", product);
                }
            }else if(domainValues.size() == 1) {
                boolean good = false;
                String domainValue = domainValues.get(0).getDomainValue();
                for(Util.Type type : Util.TYPES){
                    if(type.matches(domainValue)){
                        good = type.check(attribute.getValue().trim());
                        break;
                    }
                }
                if(!good){
                    throwExceptionAndDelete("Provided value for attribute type with name: " + attributeTypeRepository.findByAttributeTypeID(attribute.getId()).get().getName() + " is not in domain.", product);
                }

            }else{
                boolean good = false;
                for(int i = 0; i <domainValues.size();i++){
                    String domainValue = domainValues.get(i).getDomainValue();
                    if(domainValue.trim().equals(attribute.getValue().trim())){
                        good = true;
                        break;
                    }
                }
                if(!good){
                    throwExceptionAndDelete("Provided value for attribute type with name: " + attributeTypeRepository.findByAttributeTypeID(attribute.getId()).get().getName() + " is not in domain.", product);
                }
            }

            toSave.add(new ProductAttributeValue(id, attribute.getId(), attribute.getValue()));
        }

        for(ProductAttributeValue v : toSave){
            productAttributeValueRepository.save(v);
        }

        productDTO.setProductID(id);
        return productDTO;
    }

    @Override
    public ProductDTO fetchProduct(Long id) {
        return null;
    }

    @Override
    public List<ProductDTO> listProductsWithProductType(Long prodID) {
        return listProducts(repository -> repository.findByProductTypeID(prodID));
    }

    private List<ProductDTO> listProducts(Function<ProductRepository, List<Product>> function){
        List<ProductDTO> output = new ArrayList<>();

        List<Product> products = function.apply(productRepository);
        for(Product product : products){
            Long id = product.getProductID();
            List<ProductAttributeValue> productAttributeValues = productAttributeValueRepository.findByProductID(id);
            ProductDTO.LongStringPair[] pairs = new ProductDTO.LongStringPair[productAttributeValues.size()];
            for(int i = 0 ; i < productAttributeValues.size();i++){
                pairs[i] = new ProductDTO.LongStringPair(productAttributeValues.get(i).getProductAttributeID(), productAttributeValues.get(i).getValue());
            }
            output.add(new ProductDTO(id, product.getProductTypeID(), product.getName(), pairs));
        }

        return output;
    }

    private void throwExceptionAndDelete(String message, Product product){
        productRepository.delete(product);
        throw new RequestDeniedException(message);
    }
}
