package hr.fer.evaluator.service.impl;

import hr.fer.evaluator.dao.AttributeDomainRepository;
import hr.fer.evaluator.dao.AttributeTypeRepository;
import hr.fer.evaluator.domain.AttributeDomain;
import hr.fer.evaluator.domain.AttributeType;
import hr.fer.evaluator.rest.dto.AttributeTypeDTO;
import hr.fer.evaluator.service.AttributeTypeService;
import hr.fer.evaluator.service.Util;
import hr.fer.evaluator.service.exceptions.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttributeTypeServiceJpa implements AttributeTypeService {

    @Autowired
    AttributeTypeRepository attributeTypeRepository;

    @Autowired
    AttributeDomainRepository attributeDomainRepository;

    @Override
    public List<AttributeTypeDTO> listAll() {

        List<AttributeTypeDTO> output = new ArrayList<>();

        List<AttributeType> types = attributeTypeRepository.findAll();
        for(AttributeType type : types){
            Long id = type.getAttributeTypeID();
            List<AttributeDomain> domainValues = attributeDomainRepository.findByAttributeTypeID(id);
            String[] values = new String[domainValues.size()];
            for(int i = 0; i<domainValues.size();i++){
                values[i] = domainValues.get(i).getDomainValue();
            }

            output.add(new AttributeTypeDTO(id, type.getName(), type.getRangeAttribute(), values));
        }

        return output;
    }

    @Override
    public AttributeTypeDTO createAttributeType(AttributeTypeDTO attributeTypeDTO) {
        Assert.notNull(attributeTypeDTO, "Attribute type must not be null.");
        Assert.notNull(attributeTypeDTO.getName(), "Attribute name must not be null.");
        Assert.notNull(attributeTypeDTO.getRangeAttribute(), "Attribute range qualifier must not be null.");
        Assert.notNull(attributeTypeDTO.getDomainValues(), "Attribute domain values must not be null.");

        List<String> cleanDomainValues = new ArrayList<>();
        for(String value : attributeTypeDTO.getDomainValues()){
            if(!value.equals("")){
                cleanDomainValues.add(value);
            }
        }
        String[] values = new String[cleanDomainValues.size()];
        for(int i = 0; i < cleanDomainValues.size();i++){
            values[i] = cleanDomainValues.get(i);
        }

        if(attributeTypeDTO.getRangeAttribute()){
            if(values.length != 2)
                throw new RequestDeniedException("If rangeAttribute is set, must only define only 2 domain values, upper bound and lower bound.");
            if( !Util.isDouble(values[0]) || !Util.isDouble(values[1]))
                throw new RequestDeniedException("If rangeAttribute is set, domain values must be numbers.");

            String first = values[0];
            String second = values[1];
            if(Double.parseDouble(first) > Double.parseDouble(second)){
                values[0] = second;
                values[1] = first;
            }
        }

        boolean good = false;
        if(values.length == 1){
            for(Util.Type type : Util.TYPES){
                if(type.matches(attributeTypeDTO.getDomainValues()[0])){
                    good = true;
                    break;
                }
            }
            if(!good){
                throw new RequestDeniedException("If only one domain value is given, it must represent a type.");
            }
        }



        AttributeType attributeType = attributeTypeRepository.save(new AttributeType(attributeTypeDTO.getName(), attributeTypeDTO.getRangeAttribute()));
        Long id = attributeType.getAttributeTypeID();

        for(String domainValue : values){
            attributeDomainRepository.save(new AttributeDomain(id, domainValue));
        }

        attributeTypeDTO.setId(id);
        attributeTypeDTO.setDomainValues(values);
        return attributeTypeDTO;
    }

    @Override
    public AttributeTypeDTO fetchAttributeType(Long id) {


        Optional<AttributeType> value = attributeTypeRepository.findByAttributeTypeID(id);

        if(!value.isPresent()){
            return null;
        }
        AttributeType type = value.get();

        List<AttributeDomain> domainValues = attributeDomainRepository.findByAttributeTypeID(id);
        String[] values = new String[domainValues.size()];
        for(int i = 0; i<domainValues.size();i++){
            values[i] = domainValues.get(i).getDomainValue();
        }


        return new AttributeTypeDTO(id, type.getName(), type.getRangeAttribute(), values);
    }
}
