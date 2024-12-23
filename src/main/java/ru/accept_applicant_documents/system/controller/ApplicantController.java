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
//        //File docsDir = new File(applicantDir, "docs");
//
        // Создаем директории, если они не существуют
        if (!applicantDir.exists()) {
            applicantDir.mkdirs();
        }
//        if (!docsDir.exists()) {
//            docsDir.mkdirs();
//        }
//
//        file.transferTo(new File(resourcePath + applicant.getId() + "/" + fileName));

        /*
        * Сохранение фоток
        *   Создать папку с id абита
        *   Загрузить в нее все фотки
        *   Вести учет провереных абитов и создавать им личные дела
        * Отправка сотрудникам приемной комисси
        * */

        return "redirect:/applicant/lk";
    }

    //deprecated**
//    @PostMapping("/applicant/{id}/addDocument")
//    public String addApplicantDocument(@ModelAttribute("examDocument") @Valid DocumentApplicantDto documentApplicantDto, Model model, @PathVariable Long id) {
//
//        System.out.println(applicantService.addApplicantDocument(new Document(null, documentApplicantDto.getTitle(), documentApplicantDto.getDocNumber(),
//                null,null,null,null,null,null,null)));
//        return "main";
//    }

}
