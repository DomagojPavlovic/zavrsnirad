package hr.fer.evaluator.dao;

import hr.fer.evaluator.domain.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    Optional<ProductType> findByProductTypeID(Long productTypeID);
}
