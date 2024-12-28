package ru.accept_applicant_documents.system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePersonalFileDto {

    @NotNull
    private int additionalPoints;

}
