package hr.fer.evaluator.rest.dto;

public class AttributeTypeDTO {

    private Long id;
    private String name;
    private Boolean isRangeAttribute;
    private String[] domainValues;

    public AttributeTypeDTO(Long id, String name, Boolean isRangeAttribute, String[] domainValues) {
        this.id = id;
        this.name = name;
        this.isRangeAttribute = isRangeAttribute;
        this.domainValues = domainValues;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String[] getDomainValues() {
        return domainValues;
    }

    public void setDomainValues(String[] domainValues) {
        this.domainValues = domainValues;
    }
}
