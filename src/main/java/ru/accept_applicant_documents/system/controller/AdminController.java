package ru.accept_applicant_documents.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.accept_applicant_documents.system.enums.StatusesOfDocuments;
import ru.accept_applicant_documents.system.model.Admin;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.model.Document;
import ru.accept_applicant_documents.system.repository.DocumentRepo;
import ru.accept_applicant_documents.system.service.AdminService;
import ru.accept_applicant_documents.system.service.ApplicantService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    ApplicantService applicantService;
    @Autowired
    DocumentRepo documentRepo;

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

    @GetMapping("/admin/lk/applicant")
    public String getAdminLkConcreteApplicant(@RequestParam("email") String applicantEmail, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Admin admin = adminService.findByEmail(email).get();
        model.addAttribute("admin", admin);

        Applicant applicant = applicantService.findByEmail(applicantEmail).get();

        model.addAttribute("applicant", applicant);

        List<Document> docs = documentRepo.findAllByApplicant(applicant);

        List<Map<String, String>> documentDetails = new ArrayList<>();
        for (Document doc : docs) {
            File file = new File(doc.getPathToImage()); // Получаем файл по пути из документа
            if (file.exists()) {
                Map<String, String> fileInfo = new HashMap<>();
                fileInfo.put("path", "/uploads/applicants/" + applicant.getId() + "/" + file.getName());
                fileInfo.put("name", file.getName());
                documentDetails.add(fileInfo);
            }
        }

        model.addAttribute("files", documentDetails);

        return "admin-applicant";
    }

    @PostMapping("/admin/lk/applicant")
    public String postAdminLkApplicant() {

        return "redirect:/admin/lk/unchecked";
    }

}
