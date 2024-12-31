package ru.accept_applicant_documents.system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowListOfApplicants {
    private Long id;

    private String SNILS;

    private String registrationNumber;

    private int priority;

    private String scoresBtExams;

    private int summaryScore;

    private String presenceOfAdvantages;

    private String presenceOfOriginal;

}
