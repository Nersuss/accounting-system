package ru.accept_applicant_documents.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.accept_applicant_documents.system.dto.RegisterApplicantDto;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.model.PersonalFile;
import ru.accept_applicant_documents.system.repository.ApplicantRepo;
import ru.accept_applicant_documents.system.repository.DocumentRepo;
import ru.accept_applicant_documents.system.repository.ExamResultRepo;
import ru.accept_applicant_documents.system.repository.PersonalFileRepo;

import java.util.Optional;

@Service
public class PersonalFileService {

    @Autowired
    PersonalFileRepo personalFileRepo;
    @Autowired
    ExamResultRepo examResultRepo;

    @Autowired
    DocumentRepo documentRepo;

    public PersonalFile createNewPersonalFile(PersonalFile personalFile) {
        return personalFileRepo.save(personalFile);
    }

    public Optional<PersonalFile> findById(Long id) {
        return personalFileRepo.findById(id);
    }



}
