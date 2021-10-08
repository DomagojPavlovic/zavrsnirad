package hr.fer.evaluator.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProductType {

    @Id
    @GeneratedValue
    private Long productTypeID;

    private String name;

    public ProductType() {
    }

    public ProductType(String name) {
        this.name = name;
    }

    public Long getProductTypeID() {
        return productTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
