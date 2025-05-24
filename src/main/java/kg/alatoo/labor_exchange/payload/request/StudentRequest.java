package kg.alatoo.labor_exchange.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record StudentRequest(
        @NotNull(message = "Имя пользователя не должно быть пустым") @Max(value = 50, message = "Имя пользователя не должно превышать 50 символов") String username,
        @NotNull(message = "Email не должен быть пустым") @Max(value = 60, message = "Email не должен превышать 60 символов") String email,
        @NotNull(message = "Пароль не должен быть пустым") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "Пароль должен содержать хотя бы одну заглавную букву, одну строчную букву, одну цифру и один специальный символ") String password,

        @NotNull(message = "Имя не должно быть пустым") @Max(value = 50, message = "Имя не должно превышать 50 символов") @Pattern(regexp = "^[А-Яа-яЁё]+$", message = "Имя должно содержать только буквы") String firstName,

        @NotNull(message = "Фамилия не должна быть пустой") @Max(value = 50, message = "Фамилия не должна превышать 50 символов") @Pattern(regexp = "^[А-Яа-яЁё]+$", message = "Фамилия должна содержать только буквы") String lastName


) {
}
