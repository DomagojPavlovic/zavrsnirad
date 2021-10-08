package hr.fer.evaluator.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProductAttributeValue {

    @Id
    @GeneratedValue
    private Long productAttributeValueID;

    private Long productID;

    private Long productAttributeID;

    private String value;

    public ProductAttributeValue() {
    }

    public ProductAttributeValue(Long productID, Long productAttributeID, String value) {
        this.productID = productID;
        this.productAttributeID = productAttributeID;
        this.value = value;
    }

    public Long getProductAttributeValueID() {
        return productAttributeValueID;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public Long getProductAttributeID() {
        return productAttributeID;
    }

    public void setProductAttributeID(Long productAttributeID) {
        this.productAttributeID = productAttributeID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
