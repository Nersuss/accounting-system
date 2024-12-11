package ru.accept_applicant_documents.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.accept_applicant_documents.system.enums.AllSubjects;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Setter
@Getter
@Table
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    private int score;

    @NotNull
    private LocalDate date;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private AllSubjects subject;

    // Связь ManyToOne
    @ManyToOne
    @NotNull
    private Applicant applicant;

}
