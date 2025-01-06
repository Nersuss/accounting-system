package ru.accept_applicant_documents.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    @ManyToOne
    private Subject subject;

    @ManyToOne
    @NotNull
    private Applicant applicant;

}
