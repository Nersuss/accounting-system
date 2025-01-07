package ru.accept_applicant_documents.system.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.accept_applicant_documents.system.enums.StatusesOfDocuments;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.model.CompetitionGroup;
import ru.accept_applicant_documents.system.model.Order;
import ru.accept_applicant_documents.system.model.PersonalFile;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    @Transactional
    @Query("SELECT o FROM Order o WHERE o.personalFile = ?1 ORDER BY o.date ASC")
    List<Order> findAllByPersonalFile(PersonalFile personalFile);


    @Transactional
    @Modifying
    @Query("update Order o set o.assent = ?1 where o.personalFile = ?2")
    int setAssentByPersonalFile(boolean assent, PersonalFile personalFile);


    @Transactional
    @Modifying
    @Query("update Order o set o.assent = ?1 where o.personalFile = ?2 and o.competitionGroup = ?3")
    int setAssentByPersonalFileAndCompetitionGroup(boolean assent, PersonalFile personalFile, CompetitionGroup competitionGroup);


}
