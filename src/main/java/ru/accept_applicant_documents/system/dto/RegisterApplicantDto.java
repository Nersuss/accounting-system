package ru.accept_applicant_documents.system.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterApplicantDto {

    @NotEmpty(message = "Пароль не может быть пустым")
    @Size(min = 8, max=50, message = "Пароль должен содержать от 8 символов")
    private String password;

    @NotBlank(message = "Почта не может быть пустой")
    @Size(max=200, message = "Почта должна быть короче 200 символов")
    @Email(message = "Почта должна быть правильной")
    private String email;

    @NotEmpty(message = "Номер телефона не может быть пустым")
    @Size(min = 7, max=20, message = "Неправильно введен номер телефона")
    private String phone;
}
