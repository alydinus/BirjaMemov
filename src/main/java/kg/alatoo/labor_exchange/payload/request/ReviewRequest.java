package kg.alatoo.labor_exchange.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.alatoo.labor_exchange.entity.Student;
import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.exception.exceptions.EntityExists;

public record ReviewRequest(

        @EntityExists(entityClass = Tutor.class)
        @NotNull
        @NotBlank
        @Size(max = 255)
        String tutorId,

        @EntityExists(entityClass = Student.class)
        @NotNull
        @NotBlank
        @Size(max = 255)
        String studentId,

        Double rating,

        @NotNull
        @NotBlank
        @Size(max = 500)
        String comment
) {
}
