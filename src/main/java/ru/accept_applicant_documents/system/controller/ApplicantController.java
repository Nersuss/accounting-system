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
        return "applicant-lk-myapplications";
    }
    @GetMapping("/applicant/lk/application")
    public String getApplicantLkApplication(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);
        return "applicant-lk-applications";
    }
    @GetMapping("/applicant/lk/lists")
    public String getApplicantLkLists(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);
        return "applicant-lk-lists";
    }
    @GetMapping("/applicant/lk/settings")
    public String getApplicantEdit(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);
        return "applicant-lk-settings";
    }
//    @PostMapping("/applicant/lk/edit")
//    public String postApplicantEdit(Model model) {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        Applicant applicant = applicantService.findByEmail(email).get();
//        model.addAttribute("applicant", applicant);
//        return "redirect:/applicant-lk";
//    }
    @GetMapping("/applicant/lk/verification")
    public String getApplicantEditDocs(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);
        return "applicant-lk-verification";
    }
    @PostMapping("/applicant/lk/verification")
    public String postApplicantEditDocs(@RequestParam("file") MultipartFile file ) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();

        String fileName = file.getOriginalFilename();

        String resourcePath = new File("uploads/applicants/" + applicant.getId()).getAbsolutePath();

        // Создаем директорию, если она не существует
        File applicantDir = new File(resourcePath);

        if (!applicantDir.exists()) {
            if (!applicantDir.mkdirs()) {
                throw new IOException("Не удалось создать директорию: " + resourcePath);
            }
        }

        // Сохраняем файл в директорию
        File destinationFile = new File(applicantDir, fileName);
        file.transferTo(destinationFile);

        return "redirect:/applicant/lk";
    }

}
