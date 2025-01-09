package ru.accept_applicant_documents.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.accept_applicant_documents.system.dto.RegisterApplicantDto;
import ru.accept_applicant_documents.system.enums.Roles;
import ru.accept_applicant_documents.system.enums.StatusesOfDocuments;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.model.Document;
import ru.accept_applicant_documents.system.model.ExamResult;
import ru.accept_applicant_documents.system.repository.ApplicantRepo;
import ru.accept_applicant_documents.system.repository.DocumentRepo;
import ru.accept_applicant_documents.system.repository.ExamResultRepo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ApplicantService {

    @Autowired
    ApplicantRepo applicantRepo;
    @Autowired
    ExamResultRepo examResultRepo;
    @Autowired
    DocumentRepo documentRepo;

    public Applicant registerNewApplicantAccount(RegisterApplicantDto applicant) {

        return applicantRepo.save(new Applicant(null, applicant.getEmail(), applicant.getPassword(), null,
                null, null, null, null, applicant.getPhone(),
                StatusesOfDocuments.INCORRECT, Roles.APPLICANT , null));
    }

    public boolean ApplicantAccountAlreadyExist(String email) {
        if (applicantRepo.findByEmail(email).isEmpty()) {
            return false;
        }
        return true;
    }

    public Optional<Applicant> findByEmail(String email) {
        return applicantRepo.findByEmail(email);
    }

    public List<ExamResult> findExamsApplicantByApplicant(Applicant applicant) {
        return examResultRepo.findAllByApplicant(applicant);
    }

    public ExamResult addApplicantExamResult(ExamResult examResult) {
        examResultRepo.save(examResult);
        return examResult;
    }

    public Document addDocument(Document document) {
        documentRepo.save(document);
        return document;
    }

    public Optional<Applicant> findById(Long id) {
        return applicantRepo.findById(id);
    }

    public void setDocStatusByEmail(StatusesOfDocuments status, String email) {
        applicantRepo.setDocStatus(status, email);
    }

    public List<Applicant> findAllByDocStatus(StatusesOfDocuments status) {
        return applicantRepo.findAllByDocStatus(status);
    }

}
