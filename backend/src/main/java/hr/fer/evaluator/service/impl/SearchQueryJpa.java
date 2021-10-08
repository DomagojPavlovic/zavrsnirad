package hr.fer.evaluator.service.impl;

import hr.fer.evaluator.dao.*;
import hr.fer.evaluator.domain.AttributeType;
import hr.fer.evaluator.domain.SearchQuery;
import hr.fer.evaluator.domain.SearchQueryElement;
import hr.fer.evaluator.rest.dto.ProductDTO;
import hr.fer.evaluator.rest.dto.SearchQueryDTO;
import hr.fer.evaluator.service.SearchQueryService;
import hr.fer.evaluator.service.Util;
import hr.fer.evaluator.service.exceptions.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchQueryJpa implements SearchQueryService {

    @Autowired
    SearchQueryRepository searchQueryRepository;

    @Autowired
    SearchQueryElementRepository searchQueryElementRepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    AttributeTypeRepository attributeTypeRepository;

    @Autowired
    ProductServiceJpa productServiceJpa;

    @Override
    public List<SearchQueryDTO> listAll() {

        List<SearchQuery> queries = searchQueryRepository.findAll();
        List<SearchQueryDTO> output = new ArrayList<>();
        for(SearchQuery query : queries){
            List<SearchQueryElement> queryElements = searchQueryElementRepository.findBySearchQueryID(query.getSearchQueryID());

            SearchQueryDTO.SearchQueryElementDTO[] elementsArray = new SearchQueryDTO.SearchQueryElementDTO[queryElements.size()];
            for(int i = 0; i<queryElements.size();i++){
                SearchQueryElement element = queryElements.get(i);
                elementsArray[i] = new SearchQueryDTO.SearchQueryElementDTO(element.getAttributeTypeID(), element.getPriority(), element.getQueryType(), element.getQueryValue());
            }

            output.add(new SearchQueryDTO(query.getSearchQueryID(), query.getProductTypeID(), elementsArray));
        }

        return output;
    }

    @Override
    public SearchQueryDTO addNewQuery(SearchQueryDTO searchQueryDTO) {
        Assert.notNull(searchQueryDTO, "Query must not be null.");
        Assert.notNull(searchQueryDTO.getProductTypeID(), "Product type ID must not be null.");
        Assert.notNull(searchQueryDTO.getQueryElements(), "Query elements must not be null.");

        if(!productTypeRepository.findByProductTypeID(searchQueryDTO.getProductTypeID()).isPresent())
            throw new RequestDeniedException("Provided query with product type ID: " + searchQueryDTO.getProductTypeID() + " does not exist.");

        SearchQuery query = searchQueryRepository.save(new SearchQuery(searchQueryDTO.getProductTypeID()));
        Long id = query.getSearchQueryID();

        for(SearchQueryDTO.SearchQueryElementDTO element : searchQueryDTO.getQueryElements()){

            if(!attributeTypeRepository.findByAttributeTypeID(element.getAttributeTypeID()).isPresent())
                throw new RequestDeniedException("Provided query element with attribute type ID:" + element.getAttributeTypeID() + " does not exist");

            if(element.getQueryType()<0 && element.getQueryType()>=Util.COMPARISONS.length)
                throw new RequestDeniedException("Provided query element with query type:" + element.getQueryType()+ " does not exist");

            /*
            if(!attributeTypeRepository.findByAttributeTypeID(element.getAttributeTypeID()).get().getRangeAttribute() && element.getQueryType()>Util.HIGHEST_AVAILABLE_COMPARISONS_FOR_NON_RANGE )
                throw new RequestDeniedException("Cannot execute given comparison " + element.getQueryType()+ " on provided attribute type " + element.getAttributeTypeID());
            */

            if(element.getQueryValue().equals("") && element.getQueryType()==0 && attributeTypeRepository.findByAttributeTypeID(element.getAttributeTypeID()).get().getRangeAttribute()){
                element.setQueryValue("0");
            }

            if(element.getQueryType().equals("") && element.getQueryType()!=0){
                throw new RequestDeniedException("Cannot execute given query because a filter type was selected without providing a value.");
            }

            if(attributeTypeRepository.findByAttributeTypeID(element.getAttributeTypeID()).get().getRangeAttribute() && !Util.isDouble(element.getQueryValue()))
                throw new RequestDeniedException("Cannot execute given comparison " + element.getQueryType()+ " because " + element.getQueryValue() + "is not a number");


            //check if the query value is in allowed values?

            searchQueryElementRepository.save(new SearchQueryElement(id, element.getAttributeTypeID(), element.getPriority(), element.getQueryType(), element.getQueryValue()));
        }
        searchQueryDTO.setSearchQueryID(id);
        return searchQueryDTO;
    }

    @Override
    public List<ProductDTO> returnQueryResults(SearchQueryDTO searchQueryDTO) {
        List<ProductDTO> products = productServiceJpa.listProductsWithProductType(searchQueryDTO.getProductTypeID());

        SearchQueryDTO.SearchQueryElementDTO[] elements = searchQueryDTO.getQueryElements();


        Map<ProductDTO, Integer> map = new HashMap<>();



        outer: for(ProductDTO product : products){
            map.put(product, 0);

            for(SearchQueryDTO.SearchQueryElementDTO element : elements){
                Long attributeID = element.getAttributeTypeID();

                String productAttributeValue = null;
                for(ProductDTO.LongStringPair pair : product.getAttributeValues()){
                    if(pair.getId().equals(attributeID)){
                        productAttributeValue = pair.getValue();
                        break;
                    }
                }

                Comparable first = productAttributeValue;
                Comparable second = element.getQueryValue();
                if(attributeTypeRepository.findByAttributeTypeID(attributeID).get().getRangeAttribute()){
                    first = Double.parseDouble((String)first);
                    second = Double.parseDouble((String)second);
                }
                if(element.getQueryType()!=0) {
                    if (!Util.COMPARISONS[element.getQueryType()].compare(first, second)) {
                        map.put(product, map.get(product) + element.getPriority());
                    }
                }
            }
        }

        List<ProductDTO> filteredProducts = new ArrayList<>();
        for(Map.Entry<ProductDTO, Integer> entry : map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue)).collect(Collectors.toList())){
            filteredProducts.add(entry.getKey());
        }

        return filteredProducts;

        /*
        List<ComparisonRule> rules = new ArrayList<>();
        for(SearchQueryDTO.SearchQueryElementDTO element : elements){
            rules.add(new ComparisonRule(element.getAttributeTypeID(), element.getSortType(), element.getPriority()));
        }
        Collections.sort(rules);

        filteredProducts.sort(
                (o1, o2) -> {
                    for(ComparisonRule rule: rules){
                        int comparisonResult = 0;
                        Comparable first = getValueFromID(rule.attributeTypeID, o1);
                        Comparable second = getValueFromID(rule.attributeTypeID, o2);
                        if(attributeTypeRepository.findByAttributeTypeID(rule.attributeTypeID).get().getRangeAttribute()){
                            first = Double.parseDouble((String)first);
                            second = Double.parseDouble((String)second);
                        }


                        switch (rule.sortType){
                            case 1:
                                comparisonResult = first.compareTo(second);
                                break;
                            case -1:
                                comparisonResult = second.compareTo(first);
                                break;
                            default:
                        }
                        if(comparisonResult != 0)
                            return comparisonResult;
                    }
                    return 0;
                });
        return filteredProducts;
        */
    }

    @Override
    public List<SearchQueryDTO> listQueriesForUser(Long id) {
        return null;
    }

    private String getValueFromID(Long ID, ProductDTO product){
        for(ProductDTO.LongStringPair pair :product.getAttributeValues()){
            if(pair.getId().equals(ID))
                return pair.getValue();
        }
        return null;
    }

    public static class ComparisonRule implements Comparable<ComparisonRule>{

        Long attributeTypeID;
        Integer sortType;
        Integer priority;

        public ComparisonRule(Long attributeTypeID, Integer sortType, Integer priority) {
            this.attributeTypeID = attributeTypeID;
            this.sortType = sortType;
            this.priority = priority;
        }

        @Override
        public int compareTo(ComparisonRule o) {
            return priority.compareTo(o.priority);
        }
    }
}
