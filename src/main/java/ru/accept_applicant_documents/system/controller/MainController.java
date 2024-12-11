package ru.accept_applicant_documents.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.service.ApplicantService;

@Controller
public class MainController {

    @Autowired
    ApplicantService applicantService;

    @GetMapping("/")
    public String getMain(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Applicant applicant = applicantService.findByEmail(authentication.getName()).get();

        model.addAttribute("email", applicant.getEmail());

        return "main";
    }

    @PostMapping("/upload")
    public String postUploadDocument(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Applicant applicant = applicantService.findByEmail(authentication.getName()).get();

        model.addAttribute("email", applicant.getEmail());

        return "main";
    }

}