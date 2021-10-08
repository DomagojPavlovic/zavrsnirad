package hr.fer.evaluator.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AttributeType {

    @Id
    @GeneratedValue
    private Long attributeTypeID;

    private String name;

    private Boolean isRangeAttribute;

    public AttributeType() {
    }

    public AttributeType(String name, Boolean isRangeAttribute) {
        this.name = name;
        this.isRangeAttribute = isRangeAttribute;
    }

    public Long getAttributeTypeID() {
        return attributeTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRangeAttribute() {
        return isRangeAttribute;
    }

    public void setRangeAttribute(Boolean rangeAttribute) {
        isRangeAttribute = rangeAttribute;
    }
}
