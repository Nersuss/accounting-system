package ru.accept_applicant_documents.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.accept_applicant_documents.system.enums.AllSubjects;
import ru.accept_applicant_documents.system.enums.Roles;
import ru.accept_applicant_documents.system.enums.StatusesOfDocuments;
import ru.accept_applicant_documents.system.model.Admin;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.model.ExamResult;
import ru.accept_applicant_documents.system.repository.AdminRepo;
import ru.accept_applicant_documents.system.repository.ApplicantRepo;
import ru.accept_applicant_documents.system.repository.ExamResultRepo;
import ru.accept_applicant_documents.system.service.AdminService;
import ru.accept_applicant_documents.system.service.ApplicantService;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class SystemApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    ApplicantService applicantService;
    @Autowired
    AdminService adminService;
    @Autowired
    ApplicantRepo applicantRepo;
    @Autowired
    AdminRepo adminRepo;
    @Autowired
    ExamResultRepo examResultRepo;

    @Autowired
    DataSource dataSource;


    @Override
    public void run(String... args) throws Exception {
        // Создание админа по умолчанию
        if (!adminService.AdminAccountAlreadyExist("admin@mail.ru")) {
            adminRepo.save(new Admin(null, "admin@mail.ru", "Адольф",
                    bCryptPasswordEncoder.encode("12345678"), Roles.ADMIN));
        }

        Resource resource = new ClassPathResource("static/scripts/Specializations.sql");
        if (!resource.exists()) {
            throw new FileNotFoundException("SQL script not found: " + resource.getFilename());
        }

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("static/scripts/Specializations.sql")); // Укажите путь к вашему SQL файлу

        resourceDatabasePopulator.execute(dataSource);
    }

}

