package hr.fer.evaluator.rest.dto;

public class ProductDTO {

    private Long productID;
    private Long productTypeID;
    private String name;
    private LongStringPair[] attributeValues;

    public ProductDTO(Long productID, Long productTypeID, String name, LongStringPair[] attributeValues) {
        this.productID = productID;
        this.productTypeID = productTypeID;
        this.name = name;
        this.attributeValues = attributeValues;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
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

    public LongStringPair[] getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(LongStringPair[] attributeValues) {
        this.attributeValues = attributeValues;
    }

    public static class LongStringPair{
        private Long id;
        private String value;

        public LongStringPair() {
        }

        public LongStringPair(Long id, String value) {
            this.id = id;
            this.value = value;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
