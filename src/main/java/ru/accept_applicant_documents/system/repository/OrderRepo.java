package ru.accept_applicant_documents.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.model.Order;
import ru.accept_applicant_documents.system.model.PersonalFile;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findAllByPersonalFile(PersonalFile personalFile);
}
