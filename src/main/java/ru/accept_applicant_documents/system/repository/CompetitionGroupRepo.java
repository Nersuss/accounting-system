package ru.accept_applicant_documents.system.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.accept_applicant_documents.system.model.CompetitionGroup;
import ru.accept_applicant_documents.system.model.Department;

import java.util.List;

@Repository
public interface CompetitionGroupRepo extends JpaRepository<CompetitionGroup, Long> {
    CompetitionGroup findByTitle(String title);

    @Transactional
    @Query("SELECT o FROM Order o WHERE o.competitionGroup.department = ?1")
    List<CompetitionGroup> findAllByDepartment(Department department);
}
