package kg.alatoo.labor_exchange.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;

public record TutorRequest(
    @NotNull(message = "Имя пользователя не должно быть пустым") @Max(value = 50, message = "Имя пользователя не должно превышать 50 символов") String username,
    @NotNull(message = "Email не должен быть пустым") @Max(value = 60, message = "Email не должен превышать 60 символов") String email,
    @NotNull(message = "Пароль не должен быть пустым") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "Пароль должен содержать хотя бы одну заглавную букву, одну строчную букву, одну цифру и один специальный символ") String password,

    @JsonProperty("firstname")
    @NotNull(message = "Имя не должно быть пустым") @Max(value = 50, message = "Имя не должно превышать 50 символов") @Pattern(regexp = "^[А-Яа-яЁё]+$", message = "Имя должно содержать только буквы") String firstName,

    @JsonProperty("lastname")
    @NotNull(message = "Фамилия не должна быть пустой") @Max(value = 50, message = "Фамилия не должна превышать 50 символов") @Pattern(regexp = "^[А-Яа-яЁё]+$", message = "Фамилия должна содержать только буквы") String lastName,

    @Max(value = 500, message = "Описание не должно превышать 500 символов") String description,

    Integer experienceYears

) {

}
