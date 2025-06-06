package kg.alatoo.labor_exchange.exception.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.alatoo.labor_exchange.exception.exceptions.EntityExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityExistsValidator implements ConstraintValidator<EntityExists, String> {

    private static final Map<Class<?>, CrudRepository<?, String>> REPOSITORY_CACHE = new ConcurrentHashMap<>();

    @Autowired
    private Map<String, CrudRepository<?, String>> repositories;

    private Class<?> entityClass;

    @Override
    public void initialize(EntityExists constraintAnnotation) {
        this.entityClass = constraintAnnotation.entityClass();
    }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        if (id == null) {
            return true;
        }

        CrudRepository<?, String> repository = REPOSITORY_CACHE.computeIfAbsent(
                entityClass,
                clazz -> repositories.values().stream()
                        .filter(r -> clazz.equals(getEntityClassFromRepository(r)))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("No repository found for " + entityClass))
        );

        return repository.existsById(id);
    }

    private Class<?> getEntityClassFromRepository(CrudRepository<?, ?> repository) {
        return repository.getClass().getInterfaces()[0].getTypeParameters()[0].getClass();
    }
}