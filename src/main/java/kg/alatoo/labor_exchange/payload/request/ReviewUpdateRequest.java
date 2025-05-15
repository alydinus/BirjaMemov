package kg.alatoo.labor_exchange.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewUpdateRequest (
        @NotNull
        @NotBlank
        @Size(max = 255)
        String tutorId,

        @NotNull
        @NotBlank
        @Size(max = 255)
        String studentId,

        Double rating,

        @NotNull
        @NotBlank
        @Size(max = 500)
        String comment,

        @NotNull
        Boolean isActive
) {

}
