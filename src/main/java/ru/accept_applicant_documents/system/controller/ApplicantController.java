package ru.accept_applicant_documents.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.accept_applicant_documents.system.enums.StatusesOfDocuments;
import ru.accept_applicant_documents.system.enums.TypesOfDocuments;
import ru.accept_applicant_documents.system.model.*;
import ru.accept_applicant_documents.system.repository.*;
import ru.accept_applicant_documents.system.service.ApplicantService;
import ru.accept_applicant_documents.system.service.PersonalFileService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private DocumentRepo documentRepo;

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
        //model.addAttribute("applicant", applicant);

        orderRepo.setAssentByPersonalFile(false, personalFileService.findByApplicant(applicant).get());
        orderRepo.setAssentByPersonalFileAndCompetitionGroup(true, personalFileService.findByApplicant(applicant).get(),
                competitionGroupRepo.findById(competitionGroupId).get());

        return "redirect:/applicant/lk/applications";
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

    @GetMapping("/applicant/lk/lists")
    public String getApplicantLkChoice(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);

        model.addAttribute("applicant", applicant);
        model.addAttribute("departments", departmentRepo.findAll());

        return "applicant-lk-lists";
    }

    @GetMapping("/applicant/lk/lists/fulltime") //думаю удалить
    public String getApplicantLkBudget(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();

        // Получение всех CompetitionGroup
        List<CompetitionGroup> competitionGroups = competitionGroupRepo.findAll();

        model.addAttribute("applicant", applicant);
        model.addAttribute("departments", departmentRepo.findAll());
        model.addAttribute("competitionGroups", competitionGroups);

        return "fulltime";
    }


@GetMapping("/applicant/lk/lists/parttime") //думаю удалить
public String getApplicantLkPaid(Model model) {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    Applicant applicant = applicantService.findByEmail(email).orElseThrow(() -> new RuntimeException("Applicant not found"));

    // Получение всех CompetitionGroup
    List<CompetitionGroup> competitionGroups = competitionGroupRepo.findAll();

    model.addAttribute("applicant", applicant);
    model.addAttribute("departments", departmentRepo.findAll());
    model.addAttribute("competitionGroups", competitionGroups);

    return "parttime";
}

    @GetMapping("/applicant/lk/list")
    public String getApplicantLkList(@RequestParam("code") Optional<String> code, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Applicant applicant = applicantService.findByEmail(email).get();
        model.addAttribute("applicant", applicant);

        if (code.isPresent()) {
            Department department = departmentRepo.findByCode(code.get());
            //List<Order> orders = orderRepo.findAllByCompetitionGroupDepartment(department);
            List<Order> orders = orderRepo.findAllByCompetitionGroupDepartmentOrderByExamResultScoreSum(department);

            List<Document> snils = new ArrayList<>();
            List<List<ExamResult>> examResultsLists = new ArrayList<>();
            List<Integer> summScores = new ArrayList<>();

            for (Order order : orders) {
                snils.add(documentRepo.findByApplicantAndType(order.getPersonalFile().getApplicant(),
                        TypesOfDocuments.SNILS));
                List<ExamResult> examResults = examResultRepo.findAllByApplicant(order.getPersonalFile().getApplicant());
                examResultsLists.add(examResults);

                // Фильтрация и суммирование оценок
                int russianScore = examResults.stream()
                        .filter(exam -> "Русский язык".equalsIgnoreCase(exam.getSubject().getTitle()))
                        .mapToInt(ExamResult::getScore)
                        .findFirst()
                        .orElse(0);

                int mathScore = examResults.stream()
                        .filter(exam -> "Математика (профильный уровень)".equalsIgnoreCase(exam.getSubject().getTitle()))
                        .mapToInt(ExamResult::getScore)
                        .findFirst()
                        .orElseGet(() -> examResults.stream()
                                .filter(exam -> "Математика (базовый уровень)".equalsIgnoreCase(exam.getSubject().getTitle()))
                                .mapToInt(ExamResult::getScore)
                                .findFirst()
                                .orElse(0));

                List<Integer> otherScores = examResults.stream()
                        .filter(exam -> !"Русский язык".equalsIgnoreCase(exam.getSubject().getTitle())
                                && !"Математика (базовый уровень)".equalsIgnoreCase(exam.getSubject().getTitle())
                                && !"Математика (профильный уровень)".equalsIgnoreCase(exam.getSubject().getTitle()))
                        .map(ExamResult::getScore)
                        .sorted(Comparator.reverseOrder()) // Сортировка по убыванию
                        .toList();

                int totalScore;
                if (examResults.stream()
                        .anyMatch(exam -> "Математика (базовый уровень)".equalsIgnoreCase(exam.getSubject().getTitle()))) {
                    // Если есть базовая математика, учитываем два самых больших результата
                    int top1 = otherScores.size() > 0 ? otherScores.get(0) : 0;
                    int top2 = otherScores.size() > 1 ? otherScores.get(1) : 0;
                    totalScore = russianScore + mathScore + top1 + top2;
                } else {
                    // Если математика не базовая, учитываем только один самый большой результат
                    int highestOtherScore = otherScores.size() > 0 ? otherScores.get(0) : 0;
                    totalScore = russianScore + mathScore + highestOtherScore;
                }

                summScores.add(totalScore); // Добавить итоговую сумму в список
            }

            model.addAttribute("orders", orders);
            model.addAttribute("snils", snils);
            model.addAttribute("examResultsLists", examResultsLists);
            model.addAttribute("summScores", summScores); // Добавить суммы в модель
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

//        if (privilegeDocuments[0] != null)
//        {
//            File destinationFilePrivilegeDocuments = new File(applicantDir, privilegeDocuments[0].getOriginalFilename());
//            privilegeDocuments[0].transferTo(destinationFilePrivilegeDocuments);
//            applicantService.addDocument(new Document("Привелегия", destinationFilePrivilegeDocuments.getCanonicalPath(), TypesOfDocuments.INDIVIDUAL_ACHIEVEMENTS, applicant));
//        }
//        if (achievementDocuments[0] != null)
//        {
//            File destinationFileAchievementDocuments = new File(applicantDir, achievementDocuments[0].getOriginalFilename());
//            achievementDocuments[0].transferTo(destinationFileAchievementDocuments);
//            applicantService.addDocument(new Document("Достижение", destinationFileAchievementDocuments.getCanonicalPath(), TypesOfDocuments.REFERENCE, applicant));
//        }

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

        Order orderApplicant = orderRepo.findByPersonalFileAndCompetitionGroupDepartment(personalFileService.findByApplicant(applicant).get(), department);

        if (orderApplicant != null)
            return "redirect:/applicant/lk/application?error";

        boolean haveFirstRequiredSubject = false;
        boolean haveSecondRequiredSubject = false;
        boolean haveOptionalSubject = false;

        for (SubjectOfDepartment subjectOfDepartment : subjectsOfDepartments)
        {
            if (subjectOfDepartment.haveUniquePosition(subjectsOfDepartments)) {
                for (ExamResult examResult : applicantExams)
                {
                    if ((subjectOfDepartment.getSubject().getTitle() == examResult.getSubject().getTitle()) && (haveFirstRequiredSubject))
                        haveSecondRequiredSubject = true;
                    if ((subjectOfDepartment.getSubject().getTitle() == examResult.getSubject().getTitle()))
                        haveFirstRequiredSubject = true;
                }
            }
            else
            {
                for (ExamResult examResult : applicantExams)
                {
                    if (subjectOfDepartment.getSubject().getTitle() == examResult.getSubject().getTitle())
                    {
                        haveOptionalSubject = true;
                    }
                }
            }
        }

        if ((haveOptionalSubject) && (haveFirstRequiredSubject) && (haveSecondRequiredSubject))
        {
            orderRepo.save(new Order(null, LocalDateTime.now(), false, personalFileService.findByApplicant(applicant).get(),
                    competitionGroupRepo.findByTitle(program + "-" + studyType + "-" + studyForm)));
        }
        else
        {
            return "redirect:/applicant/lk/application?error";
        }
        return "redirect:/applicant/lk/applications";
    }

}
