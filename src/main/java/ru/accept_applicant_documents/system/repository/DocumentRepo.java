package ru.accept_applicant_documents.system.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.accept_applicant_documents.system.enums.StatusesOfDocuments;
import ru.accept_applicant_documents.system.enums.TypesOfDocuments;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.model.Document;
import ru.accept_applicant_documents.system.model.ExamResult;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {

    List<Document> findAllByApplicant(Applicant applicant);
    Document findByApplicantAndType(Applicant applicant, TypesOfDocuments typesOfDocuments);

    @Transactional
    @Modifying
    @Query("update Document d set d.series = ?1 where d.applicant = ?2 and d.type = ?3")
    int setSeriesByApplicantAndType(String series, Applicant applicant, TypesOfDocuments type);

    List<Document> findAllByApplicantAndType(Applicant applicant, TypesOfDocuments typesOfDocuments);
}