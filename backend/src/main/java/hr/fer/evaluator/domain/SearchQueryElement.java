package hr.fer.evaluator.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SearchQueryElement {

    @Id
    @GeneratedValue
    private Long searchQueryElementID;

    private Long searchQueryID;

    private Long attributeTypeID;

    private Integer priority;

    private Integer queryType;

    private String queryValue;


    public SearchQueryElement() {
    }

    public SearchQueryElement(Long searchQueryID, Long attributeTypeID, Integer priority, Integer queryType, String queryValue) {
        this.searchQueryID = searchQueryID;
        this.attributeTypeID = attributeTypeID;
        this.priority = priority;
        this.queryType = queryType;
        this.queryValue = queryValue;
    }

    public Long getSearchQueryElementID() {
        return searchQueryElementID;
    }

    public Long getSearchQueryID() {
        return searchQueryID;
    }

    public void setSearchQueryID(Long searchQueryID) {
        this.searchQueryID = searchQueryID;
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
