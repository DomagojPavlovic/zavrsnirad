package hr.fer.evaluator.rest;

import hr.fer.evaluator.rest.dto.AttributeTypeDTO;
import hr.fer.evaluator.service.AttributeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attributeTypes")
public class AttributeTypeController {

    @Autowired
    AttributeTypeService attributeTypeService;

    @PostMapping
    public AttributeTypeDTO createAttributeType(@RequestBody AttributeTypeDTO attributeTypeDTO){
        return attributeTypeService.createAttributeType(attributeTypeDTO);
    }

    @GetMapping
    public List<AttributeTypeDTO> listAttributeTypes(){
        return attributeTypeService.listAll();
    }

    @GetMapping("/{id}")
    public AttributeTypeDTO getAttributeType(@PathVariable("id") Long id){
        return attributeTypeService.fetchAttributeType(id);
    }

}
