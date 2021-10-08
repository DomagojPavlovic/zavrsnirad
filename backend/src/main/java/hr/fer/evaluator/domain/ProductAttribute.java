package hr.fer.evaluator.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProductAttribute {

    @Id
    @GeneratedValue
    private Long productAttributeID;

    private Long attributeTypeID;

    private Long productTypeID;

    public ProductAttribute() {
    }

    public ProductAttribute(Long attributeTypeID, Long productTypeID) {
        this.attributeTypeID = attributeTypeID;
        this.productTypeID = productTypeID;
    }

    public Long getProductAttributeID() {
        return productAttributeID;
    }

    public Long getAttributeTypeID() {
        return attributeTypeID;
    }

    public void setAttributeTypeID(Long attributeTypeID) {
        this.attributeTypeID = attributeTypeID;
    }

    public Long getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Long productTypeID) {
        this.productTypeID = productTypeID;
    }
}
