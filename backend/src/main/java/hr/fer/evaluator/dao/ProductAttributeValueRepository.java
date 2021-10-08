package hr.fer.evaluator.dao;

import hr.fer.evaluator.domain.ProductAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductAttributeValueRepository extends JpaRepository<ProductAttributeValue, Long> {
    Optional<ProductAttributeValue> findByProductAttributeValueID(Long productAttributeValueID);
    List<ProductAttributeValue> findByProductID(Long productID);
}
