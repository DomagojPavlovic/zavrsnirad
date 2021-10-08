package hr.fer.evaluator.dao;

import hr.fer.evaluator.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductID(Long productID);
    List<Product> findByProductTypeID(Long productTypeID);
}
