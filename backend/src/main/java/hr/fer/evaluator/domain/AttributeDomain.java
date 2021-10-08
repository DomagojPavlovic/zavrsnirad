package hr.fer.evaluator.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AttributeDomain {

    @Id
    @GeneratedValue
    private Long attributeDomainID;

    private Long attributeTypeID;

    private String domainValue;

    public AttributeDomain() {
    }

    public AttributeDomain(Long attributeTypeID, String domainValue) {
        this.attributeTypeID = attributeTypeID;
        this.domainValue = domainValue;
    }

    public Long getAttributeDomainID() {
        return attributeDomainID;
    }

    public Long getAttributeTypeID() {
        return attributeTypeID;
    }

    public void setAttributeTypeID(Long attributeTypeID) {
        this.attributeTypeID = attributeTypeID;
    }

    public String getDomainValue() {
        return domainValue;
    }

    public void setDomainValue(String domainValue) {
        this.domainValue = domainValue;
    }
}
