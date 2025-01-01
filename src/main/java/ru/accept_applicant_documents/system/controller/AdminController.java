package ru.accept_applicant_documents.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.accept_applicant_documents.system.enums.StatusesOfDocuments;
import ru.accept_applicant_documents.system.model.Admin;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.service.AdminService;
import ru.accept_applicant_documents.system.service.ApplicantService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    ApplicantService applicantService;

    @GetMapping("/admin/lk")
    public String getAdminLk(Model model)
    {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Admin admin = adminService.findByEmail(email).get();
        model.addAttribute("admin", admin);
        return "admin-lk";
    }
    @GetMapping("/admin/lk/unchecked")
    public String getAdminUnchecked(Model model)
    {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Admin admin = adminService.findByEmail(email).get();
        model.addAttribute("admin", admin);

        List<Applicant> applicants = applicantService.findAllByDocStatus(StatusesOfDocuments.UNCHECKED);

        List<String> emails = applicants.stream()
                .map(Applicant::getEmail)
                .collect(Collectors.toList());

        model.addAttribute("emails", emails);

        return "admin-unchecked";
    }
    @GetMapping("/admin/lk/incorrect")
    public String getAdminIncorrect(Model model)
    {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Admin admin = adminService.findByEmail(email).get();
        model.addAttribute("admin", admin);

        List<Applicant> applicants = applicantService.findAllByDocStatus(StatusesOfDocuments.INCORRECT);

        List<String> emails = applicants.stream()
                .map(Applicant::getEmail)
                .collect(Collectors.toList());

        model.addAttribute("emails", emails);

        return "admin-incorrect";
    }
    @GetMapping("/admin/lk/verified")
    public String getAdminVerified(Model model)
    {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Admin admin = adminService.findByEmail(email).get();
        model.addAttribute("admin", admin);

        List<Applicant> applicants = applicantService.findAllByDocStatus(StatusesOfDocuments.VERIFIED);

        List<String> emails = applicants.stream()
                .map(Applicant::getEmail)
                .collect(Collectors.toList());

        model.addAttribute("emails", emails);

        return "admin-verified";
    }
    @GetMapping("/admin/lk/settings")
    public String getAdminLkSettings(Model model)
    {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Admin admin = adminService.findByEmail(email).get();
        model.addAttribute("admin", admin);
        return "admin-lk-settings";
    }

    @GetMapping("/admin/lk/concreteapplicant")
    public String getAdminLkConcreteApplicant(Model model)
    {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Admin admin = adminService.findByEmail(email).get();
        model.addAttribute("admin", admin);
        return "admin-concreteapplicant";
    }

}
