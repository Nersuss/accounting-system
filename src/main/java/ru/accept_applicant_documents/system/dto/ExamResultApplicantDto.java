package ru.accept_applicant_documents.system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamResultApplicantDto {

    //@NotEmpty(message = "Password cannot be empty")
    private int score;

    //@NotBlank(message = "Email cannot be empty")
    private String subject;
}