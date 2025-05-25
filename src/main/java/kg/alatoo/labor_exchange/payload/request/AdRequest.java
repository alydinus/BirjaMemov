package kg.alatoo.labor_exchange.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.alatoo.labor_exchange.enumeration.Role;

public record AdRequest(
        @NotNull
        @NotBlank
        @Size(max = 255)
        String username,

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
        Integer price,

        @NotNull
        Role type

) {
}
