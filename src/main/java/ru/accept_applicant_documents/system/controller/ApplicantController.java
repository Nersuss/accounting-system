package ru.accept_applicant_documents.system.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.accept_applicant_documents.system.dto.DocumentApplicantDto;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.model.Document;
import ru.accept_applicant_documents.system.service.ApplicantService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Controller
public class ApplicantController {
    @Autowired
    ApplicantService applicantService;
    @GetMapping("/applicant/lk")
    public String getApplicantLk(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);
        return "applicant-lk";
    }
    @GetMapping("/applicant/lk/edit")
    public String getApplicantEdit(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);
        return "applicant-lk-edit";
    }
    @PostMapping("/applicant/lk/edit")
    public String postApplicantEdit(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);
        return "redirect:/applicant-lk";
    }
    @GetMapping("/applicant/lk/edit/docs")
    public String getApplicantEditDocs(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);
        return "applicant-lk-edit-docs";
    }
    @PostMapping("/applicant/lk/edit/docs")
    public String postApplicantEditDocs(@RequestParam("file") MultipartFile file ) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();

        String fileName = file.getOriginalFilename();

        String resourcePath = new File("src/main/resources/applicants/" + applicant.getId()).getAbsolutePath();

        File applicantDir = new File(resourcePath);

        if (!applicantDir.exists()) {
            applicantDir.mkdirs();
        }

        file.transferTo(new File(applicantDir + fileName));

        return "redirect:/applicant/lk";
    }

}
