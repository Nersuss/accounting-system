package ru.accept_applicant_documents.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.accept_applicant_documents.system.enums.AllSubjects;
import ru.accept_applicant_documents.system.model.ExamResult;
import ru.accept_applicant_documents.system.repository.ExamResultRepo;
import ru.accept_applicant_documents.system.service.ApplicantService;

import java.time.LocalDate;

@SpringBootApplication
public class SystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SystemApplication.class, args);
	}


	@Autowired
	ApplicantService applicantService;

	@Autowired
	ExamResultRepo examResultRepo;

	@Override
	public void run(String... args) throws Exception {
		//examResultRepo.save(new ExamResult(null, 99, LocalDate.now(), AllSubjects.RUSSIAN_LANGUAGE, applicantService.findById(1L).get()));
		//System.out.println(applicantService.findExamsApplicantByApplicant(applicantService.findById(1L).get()).toString());

	}
}
