package ru.accept_applicant_documents.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.accept_applicant_documents.system.model.Department;
import ru.accept_applicant_documents.system.model.SubjectOfDepartment;

import java.util.List;

@Repository
public interface SubjectOfDepartmentRepo extends JpaRepository<SubjectOfDepartment, Long> {
    List<SubjectOfDepartment> findAllByDepartment(Department department);
}