package kg.alatoo.labor_exchange.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.enumeration.Role;
import kg.alatoo.labor_exchange.exception.exceptions.EntityExists;

public record AdRequest(
        @EntityExists(entityClass = Tutor.class)
        @NotNull
        @NotBlank
        @Size(max = 255)
        String userId,

        @NotNull
        @NotBlank
        @Size(max = 100)
        String title,

        @NotNull
        @NotBlank
        @Size(max = 500)
        String description,

        @NotNull
        @NotBlank
        @Size(max = 50)
        String lessonName,

        @NotNull
        Integer hourlyPay,

        @NotNull
        Role type

) {
}
