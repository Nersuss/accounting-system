package ru.accept_applicant_documents.system.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterApplicantDto {

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, max=50, message = "Password should be greater then 8")
    private String password;

    @NotBlank(message = "Email cannot be empty")
    @Size(max=200, message = "Email should be less than 200")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Phone cannot be empty")
    @Size(min = 8, max=20, message = "Phone should be valid")
    private String phone;
}
