package ru.accept_applicant_documents.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.accept_applicant_documents.system.enums.Roles;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String email;

    @NotNull
    private String FIO;

    @NotNull
    private String password;

    @NotNull
    private Roles role;
}