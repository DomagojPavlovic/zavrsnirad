package hr.fer.evaluator.dao;

import hr.fer.evaluator.domain.AttributeDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttributeDomainRepository extends JpaRepository<AttributeDomain, Long> {
    List<AttributeDomain> findByAttributeTypeID(Long attributeTypeID);
}
