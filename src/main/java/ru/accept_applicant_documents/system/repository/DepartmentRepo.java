package ru.accept_applicant_documents.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.accept_applicant_documents.system.model.Department;

import java.util.List;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {
    //List<Department> findAll();
}
