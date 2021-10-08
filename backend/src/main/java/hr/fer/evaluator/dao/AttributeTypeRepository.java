package hr.fer.evaluator.dao;

import hr.fer.evaluator.domain.AttributeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttributeTypeRepository extends JpaRepository<AttributeType, Long> {
    Optional<AttributeType> findByAttributeTypeID(Long attributeTypeID);
}
