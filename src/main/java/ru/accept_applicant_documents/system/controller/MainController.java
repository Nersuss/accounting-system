package ru.accept_applicant_documents.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.repository.ApplicantRepo;
import ru.accept_applicant_documents.system.service.ApplicantService;

@Controller
public class MainController {

    @Autowired
    ApplicantService applicantService;

    @GetMapping("/")
    public String getMain() {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Applicant applicant = applicantService.findByEmail(authentication.getName()).get();
        //model.addAttribute("email", applicant.getEmail());

        return "landing";
    }

    @GetMapping("/lk")
    public String getLk() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("APPLICANT"))) {
            return "redirect:/applicant/lk/applications";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            return "redirect:/admin/lk/incorrect";
        }



        return "redirect:/";
    }


}