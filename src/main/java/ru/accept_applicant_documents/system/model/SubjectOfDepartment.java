package ru.accept_applicant_documents.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectOfDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int positionOfSubject;

    @NotNull
    @ManyToOne
    private Subject subject;

    @ManyToOne
    @NotNull
    private Department department;

    public boolean haveUniquePosition(List<SubjectOfDepartment> subjectsOfDepartment){
        for (SubjectOfDepartment subject : subjectsOfDepartment)
        {
            if ((positionOfSubject == subject.getPositionOfSubject()) && (id != subject.getId()))
                return false;
        }
        return true;
    }
}