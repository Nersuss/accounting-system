package ru.accept_applicant_documents.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.accept_applicant_documents.system.enums.AllSubjects;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int positionOfSubject;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AllSubjects subject;

    // Связь ManyToOne
    @ManyToOne
    @NotNull
    private Department department;
}