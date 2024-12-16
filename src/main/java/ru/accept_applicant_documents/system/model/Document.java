package ru.accept_applicant_documents.system.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.accept_applicant_documents.system.enums.TypesOfDocuments;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    private String docNumber;

    private String series;

    private String issuedBy;

    private LocalDateTime dateOfSubmission;

    private String pathToImage;

    private String data;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypesOfDocuments type;

    @ManyToOne
    @NotNull
    private Applicant applicant;

}
