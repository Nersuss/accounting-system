package ru.accept_applicant_documents.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private LocalDateTime date;

    private boolean assent;

    // Связь ManyToOne
    @ManyToOne
    @NotNull
    private PersonalFile personalFile;

    // Связь ManyToOne
    @ManyToOne
    @NotNull
    private CompetitionGroup competitionGroup;

}
