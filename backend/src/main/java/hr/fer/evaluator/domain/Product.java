package hr.fer.evaluator.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long productID;

    private Long productTypeID;

    private String name;

    public Product() {
    }

    public Product(Long productTypeID, String name) {
        this.productTypeID = productTypeID;
        this.name = name;
    }

    public Long getProductID() {
        return productID;
    }


    public Long getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Long productTypeID) {
        this.productTypeID = productTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
