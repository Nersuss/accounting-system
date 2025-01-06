package ru.accept_applicant_documents.system.controller;

import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.accept_applicant_documents.system.dto.CreatePersonalFileDto;
import ru.accept_applicant_documents.system.dto.DocumentApplicantDto;
import ru.accept_applicant_documents.system.dto.ExamResultApplicantDto;
import ru.accept_applicant_documents.system.dto.RegisterApplicantDto;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.model.Document;
import ru.accept_applicant_documents.system.model.ExamResult;
import ru.accept_applicant_documents.system.model.PersonalFile;
import ru.accept_applicant_documents.system.repository.ExamResultRepo;
import ru.accept_applicant_documents.system.service.ApplicantService;
import ru.accept_applicant_documents.system.service.PersonalFileService;

import java.time.LocalDate;
@Controller
public class PersonalFileController { ///deprecated**

    @Autowired
    ApplicantService applicantService;

    @Autowired
    PersonalFileService personalFileService;

    @GetMapping("/personalFile/{id}")
    public String getPersonalFile(@PathVariable Long id, Model model) {

        PersonalFile personalFile = personalFileService.findById(id).get();

        model.addAttribute("personalFile", personalFile);

        return "main";
    }

    @PostMapping("/personalFile/{id}/addDocument")
    public String addApplicantDocument(@ModelAttribute("examDocument") @Valid DocumentApplicantDto documentApplicantDto, Model model, @PathVariable Long id) {

        //System.out.println(applicantService.addApplicantDocument(new Document(null, documentApplicantDto.getTitle(), documentApplicantDto.getDocNumber(),
                //null,null,null,null,null,null,null)));

        return "main";
    }

    @PostMapping("/applicant/{id}/addPersonalFile")
    public String addPersonalFile(@ModelAttribute("personalFile") @Valid CreatePersonalFileDto createPersonalFileDto, Model model, @PathVariable Long id) {

        personalFileService.createNewPersonalFile(new PersonalFile(null, 123L, createPersonalFileDto.getAdditionalPoints(), applicantService.findById(id).get()));

        return "main";
    }
}
