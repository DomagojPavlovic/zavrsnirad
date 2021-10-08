package hr.fer.evaluator.dao;

import hr.fer.evaluator.domain.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    List<ProductAttribute> findByProductTypeID(Long productTypeID);
}
