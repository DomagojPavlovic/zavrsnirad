package hr.fer.evaluator.rest.dto;

public class SearchQueryDTO {

    private Long searchQueryID;
    private Long productTypeID;
    private SearchQueryElementDTO[] queryElements;

    public SearchQueryDTO(Long searchQueryID, Long productTypeID, SearchQueryElementDTO[] queryElements) {
        this.searchQueryID = searchQueryID;
        this.productTypeID = productTypeID;
        this.queryElements = queryElements;
    }

    public SearchQueryElementDTO[] getQueryElements() {
        return queryElements;
    }

    public void setQueryElements(SearchQueryElementDTO[] queryElements) {
        this.queryElements = queryElements;
    }

    public Long getSearchQueryID() {
        return searchQueryID;
    }

    public void setSearchQueryID(Long searchQueryID) {
        this.searchQueryID = searchQueryID;
    }

    public Long getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Long productTypeID) {
        this.productTypeID = productTypeID;
    }

    public static class SearchQueryElementDTO{

        private Long attributeTypeID;
        private Integer priority;
        private Integer queryType;
        private String queryValue;

        public SearchQueryElementDTO() {
        }

        public SearchQueryElementDTO(Long attributeTypeID, Integer priority, Integer queryType, String queryValue) {
            this.attributeTypeID = attributeTypeID;
            this.priority = priority;
            this.queryType = queryType;
            this.queryValue = queryValue;
        }

        public Long getAttributeTypeID() {
            return attributeTypeID;
        }

        public void setAttributeTypeID(Long attributeTypeID) {
            this.attributeTypeID = attributeTypeID;
        }

        public Integer getPriority() {
            return priority;
        }

        public void setPriority(Integer priority) {
            this.priority = priority;
        }

        public Integer getQueryType() {
            return queryType;
        }

        public void setQueryType(Integer queryType) {
            this.queryType = queryType;
        }

        public String getQueryValue() {
            return queryValue;
        }

        public void setQueryValue(String queryValue) {
            this.queryValue = queryValue;
        }

    }
}
