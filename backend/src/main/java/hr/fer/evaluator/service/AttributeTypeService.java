package hr.fer.evaluator.service;

import hr.fer.evaluator.domain.AttributeType;
import hr.fer.evaluator.rest.dto.AttributeTypeDTO;

import java.util.List;

public interface AttributeTypeService {

    List<AttributeTypeDTO> listAll();

    AttributeTypeDTO createAttributeType(AttributeTypeDTO attributeTypeDTO);

    AttributeTypeDTO fetchAttributeType(Long id);
}
