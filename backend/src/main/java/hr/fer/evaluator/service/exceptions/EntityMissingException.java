package hr.fer.evaluator.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityMissingException extends RuntimeException{
    public EntityMissingException(Class<?> cls, Object ref) {
        super("Entity with reference " + ref + " from class " + cls + " was not found.");
    }
}
