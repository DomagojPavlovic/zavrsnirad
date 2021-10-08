package hr.fer.evaluator.rest;

import hr.fer.evaluator.rest.dto.ProductDTO;
import hr.fer.evaluator.rest.dto.SearchQueryDTO;
import hr.fer.evaluator.service.SearchQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/searchQueries")
public class SearchQueryController {

    @Autowired
    SearchQueryService searchQueryService;

    @PostMapping
    public List<ProductDTO> executeQuery(@RequestBody SearchQueryDTO searchQueryDTO){
        searchQueryService.addNewQuery(searchQueryDTO);
        return searchQueryService.returnQueryResults(searchQueryDTO);
    }

    @GetMapping("/history")
    public List<SearchQueryDTO> listAll(){
        return searchQueryService.listAll();
    }

}
