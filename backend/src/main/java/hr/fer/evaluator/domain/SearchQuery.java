package hr.fer.evaluator.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SearchQuery {

    @Id
    @GeneratedValue
    private Long searchQueryID;

    private Long productTypeID;

    public SearchQuery() {
    }

    public SearchQuery(Long productTypeID) {
        this.productTypeID = productTypeID;
    }

    public Long getSearchQueryID() {
        return searchQueryID;
    }

    public Long getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Long productTypeID) {
        this.productTypeID = productTypeID;
    }
}
