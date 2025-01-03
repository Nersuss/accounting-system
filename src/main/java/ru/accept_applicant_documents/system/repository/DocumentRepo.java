package ru.accept_applicant_documents.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.model.Document;
import ru.accept_applicant_documents.system.model.ExamResult;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {

    List<Document> findAllByApplicant(Applicant applicant);
}