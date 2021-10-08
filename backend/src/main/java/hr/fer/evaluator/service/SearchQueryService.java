package hr.fer.evaluator.service;

import hr.fer.evaluator.domain.SearchQuery;
import hr.fer.evaluator.rest.dto.ProductDTO;
import hr.fer.evaluator.rest.dto.SearchQueryDTO;

import java.util.List;

public interface SearchQueryService {

    List<SearchQueryDTO> listAll();

    SearchQueryDTO addNewQuery(SearchQueryDTO searchQueryDTO);

    List<ProductDTO> returnQueryResults(SearchQueryDTO searchQueryDTO);

    List<SearchQueryDTO> listQueriesForUser(Long id);
}
