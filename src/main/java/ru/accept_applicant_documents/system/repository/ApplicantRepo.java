package ru.accept_applicant_documents.system.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.accept_applicant_documents.system.enums.StatusesOfDocuments;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.model.ExamResult;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicantRepo extends JpaRepository<Applicant, Long> {


    Optional<Applicant> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update Applicant a set a.docStatus = ?1 where a.email = ?2")
    int setDocStatus(StatusesOfDocuments docStatus, String email);


}
