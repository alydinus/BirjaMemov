package kg.alatoo.labor_exchange.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubjectUpdateRequest(
    @NotBlank
    @NotNull
    String id,
    @NotBlank
    @NotNull
    String name
) {

}
