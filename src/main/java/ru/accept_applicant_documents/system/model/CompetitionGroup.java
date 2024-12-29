package ru.accept_applicant_documents.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.accept_applicant_documents.system.enums.Categories;
import ru.accept_applicant_documents.system.enums.FormsOfEducation;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    private int quantity;

    @ManyToOne
    @NotNull
    private Department department;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Categories category;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FormsOfEducation formOfEducation;
}