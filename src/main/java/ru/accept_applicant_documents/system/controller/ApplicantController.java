package ru.accept_applicant_documents.system.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.accept_applicant_documents.system.dto.DocumentApplicantDto;
import ru.accept_applicant_documents.system.dto.ShowListOfApplicants;
import ru.accept_applicant_documents.system.enums.StatusesOfDocuments;
import ru.accept_applicant_documents.system.enums.TypesOfDocuments;
import ru.accept_applicant_documents.system.model.*;
import ru.accept_applicant_documents.system.repository.*;
import ru.accept_applicant_documents.system.service.ApplicantService;
import ru.accept_applicant_documents.system.service.PersonalFileService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ApplicantController {
    @Autowired
    ApplicantService applicantService;

    @Autowired
    DepartmentRepo departmentRepo;

    @Autowired
    ExamResultRepo examResultRepo;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    PersonalFileService personalFileService;
    @Autowired
    CompetitionGroupRepo competitionGroupRepo;
    @Autowired
    SubjectOfDepartmentRepo subjectOfDepartmentRepo;

    @GetMapping("/applicant/lk/applications")
    public String getApplicantLk(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);

        if (personalFileService.findByApplicant(applicant).isPresent())
        {
            List<Order> orders = orderRepo.findAllByPersonalFile(personalFileService.findByApplicant(applicant).get());
            model.addAttribute("status", "VERIFIED");
            model.addAttribute("orders", orders);
        }
        model.addAttribute("status", "UNVERIFIED");



        return "applicant-lk-applications";
    }

    @PostMapping("/applicant/lk/applications")
    public String postApplicantLkApplications(@RequestParam("competitionGroupId") Long competitionGroupId,
                                              Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);

        orderRepo.setAssent(true, personalFileService.findByApplicant(applicant).get(),
                competitionGroupRepo.findById(competitionGroupId).get());

        return "redirect:/applicant-lk-applications";
    }

    @GetMapping("/applicant/lk/application")
    public String getApplicantLkApplication(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);

        model.addAttribute("departments", departmentRepo.findAll());
        //List<ExamResult> ll = examResultRepo.findAllByApplicant(applicant);
        model.addAttribute("subjects", examResultRepo.findAllByApplicant(applicant));


        return "applicant-lk-application";
    }

    @GetMapping("/applicant/lk/choice")
    public String getApplicantLkChoice(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);

        return "applicant-lk-choice";
    }

    @GetMapping("/applicant/lk/choice/budget-lists")
    public String getApplicantLkBudget(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);
        model.addAttribute("departments", departmentRepo.findAll());

        return "budget-lists";
    }

    @GetMapping("/applicant/lk/choice/paid-lists")
    public String getApplicantLkPaid(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);
        model.addAttribute("departments", departmentRepo.findAll());

        return "paid-lists";
    }

    @GetMapping("/applicant/lk/list")
    public String getApplicantLkList(@RequestParam("code") Optional<String> code, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);

        if (code.isPresent()) {

            ShowListOfApplicants list = new ShowListOfApplicants();


            model.addAttribute("list", list);
        }

        return "applicant-lk-list";
    }

    @GetMapping("/applicant/lk/applications/list")
    public String getApplicantLkApplicationsList(@RequestParam("code") Optional<String> code, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);

        if (code.isPresent()) {

            ShowListOfApplicants list = new ShowListOfApplicants();


            model.addAttribute("list", list);
        }

        return "applicant-lk-list";
    }

    @GetMapping("/applicant/lk/settings")
    public String getApplicantEdit(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);
        return "applicant-lk-settings";
    }

    @GetMapping("/applicant/lk/verification")
    public String getApplicantEditDocs(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();

        model.addAttribute("applicant", applicant);
        String docStatus = applicant.getDocStatus().name();
        model.addAttribute("status", docStatus); // Используйте docsStatus вместо docs-status (допустимый синтаксис)

        return "applicant-lk-verification";
    }

    @PostMapping("/applicant/lk/verification")
    public String postApplicantEditDocs(
            @RequestParam("pasportpicture") MultipartFile pasportpicture,
            @RequestParam("snilspicture") MultipartFile snilspicture,
            @RequestParam("educationCertificate") MultipartFile educationCertificate,
            @RequestParam(value = "specialPrivileges", required = false) Boolean specialPrivileges,
            @RequestParam(value = "bonusReason", required = false) String bonusReason,
            @RequestParam(value = "privilegeDocuments", required = false) MultipartFile privilegeDocuments,
            @RequestParam(value = "achievements", required = false) Boolean achievements,
            @RequestParam(value = "achievementReason", required = false) String achievementReason,
            @RequestParam(value = "achievementDocuments", required = false) MultipartFile achievementDocuments) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();

        String resourcePath = new File("uploads/applicants/" + applicant.getId()).getAbsolutePath();

        // Создаем директорию, если она не существует
        File applicantDir = new File(resourcePath);

        if (!applicantDir.exists()) {
            if (!applicantDir.mkdirs()) {
                throw new IOException("Не удалось создать директорию: " + resourcePath);
            }
        }

        File destinationFilePasport = new File(applicantDir, pasportpicture.getOriginalFilename());
        File destinationFileSnils = new File(applicantDir, snilspicture.getOriginalFilename());
        File destinationFileCertificate = new File(applicantDir, educationCertificate.getOriginalFilename());

        pasportpicture.transferTo(destinationFilePasport);
        snilspicture.transferTo(destinationFileSnils);
        educationCertificate.transferTo(destinationFileCertificate);

        applicantService.addDocument(new Document("Паспорт", destinationFilePasport.getCanonicalPath(), TypesOfDocuments.PASSPORT, applicant));
        applicantService.addDocument(new Document("СНИЛС", destinationFileSnils.getCanonicalPath(), TypesOfDocuments.SNILS, applicant));
        applicantService.addDocument(new Document("Аттестат", destinationFileCertificate.getCanonicalPath(), TypesOfDocuments.CERTIFICATE, applicant));

        applicantService.setDocStatusByEmail(StatusesOfDocuments.UNCHECKED, email);

        return "redirect:/applicant/lk/applications";
    }

    @PostMapping("/applicant/lk/application")
    public String postApplicantLkApplication(
            @RequestParam("program") String program,
            @RequestParam("studyForm") String studyForm,
            @RequestParam("studyType") String studyType) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        Department department = departmentRepo.findByTitle(program);

        List<ExamResult> applicantExams = examResultRepo.findAllByApplicant(applicant);

        List<SubjectOfDepartment> subjectsOfDepartments = subjectOfDepartmentRepo.findAllByDepartment(department);

        // Проверка соответствия экзаменов
        List<Subject> requiredSubjects = subjectsOfDepartments.stream()
                .map(SubjectOfDepartment::getSubject)
                .toList();

        List<Subject> applicantSubjects = applicantExams.stream()
                .map(ExamResult::getSubject)
                .toList();

        // Поиск отсутствующих предметов
        List<Subject> missingSubjects = requiredSubjects.stream()
                .filter(subject -> !applicantSubjects.contains(subject))
                .toList();

        if (!missingSubjects.isEmpty()) {
            String missingSubjectsTitles = missingSubjects.stream()
                    .map(Subject::getTitle)
                    .collect(Collectors.joining(", "));

            // Уведомление пользователя о несоответствии
            //return "redirect:/applicant/lk/applications?error=Missing subjects: " + missingSubjectsTitles;
        }

                orderRepo.save(new Order(null, LocalDateTime.now(), false, personalFileService.findByApplicant(applicant).get(),
                competitionGroupRepo.findByTitle(program + "-" + studyType + "-" + studyForm)));


        return "redirect:/applicant/lk/applications";
    }

}
