package hr.fer.evaluator.dao;

import hr.fer.evaluator.domain.SearchQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchQueryRepository extends JpaRepository<SearchQuery, Long> {
}
