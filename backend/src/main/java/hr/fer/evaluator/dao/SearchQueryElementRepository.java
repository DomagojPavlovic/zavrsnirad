package hr.fer.evaluator.dao;

import hr.fer.evaluator.domain.SearchQueryElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchQueryElementRepository extends JpaRepository<SearchQueryElement, Long> {
    List<SearchQueryElement> findBySearchQueryID(Long searchQueryID);
}
