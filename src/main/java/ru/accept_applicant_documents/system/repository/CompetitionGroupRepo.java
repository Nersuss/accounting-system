package ru.accept_applicant_documents.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.accept_applicant_documents.system.model.CompetitionGroup;

@Repository
public interface CompetitionGroupRepo extends JpaRepository<CompetitionGroup, Long> {
}
