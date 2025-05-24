package kg.alatoo.labor_exchange.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SubjectCreateRequest(
    @NotNull
    @NotBlank
    @Size(max = 255)
    String name
) {

}
