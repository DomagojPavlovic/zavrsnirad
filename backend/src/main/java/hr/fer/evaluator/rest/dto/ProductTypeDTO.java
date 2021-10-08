package hr.fer.evaluator.rest.dto;

public class ProductTypeDTO {

    private Long id;
    private String name;
    private Long[] attributeIDs;

    public ProductTypeDTO(Long id, String name, Long[] attributeIDs) {
        this.id = id;
        this.name = name;
        this.attributeIDs = attributeIDs;
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

    public Long[] getAttributeIDs() {
        return attributeIDs;
    }

    public void setAttributeIDs(Long[] attributeIDs) {
        this.attributeIDs = attributeIDs;
    }
}
