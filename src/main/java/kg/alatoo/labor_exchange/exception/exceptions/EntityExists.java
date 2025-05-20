package kg.alatoo.labor_exchange.exception.exceptions;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.alatoo.labor_exchange.exception.validator.EntityExistsValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EntityExistsValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityExists {
    String message() default "Entity with ID ${validatedValue} does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> entityClass();
}