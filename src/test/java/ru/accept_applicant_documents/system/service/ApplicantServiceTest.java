package ru.accept_applicant_documents.system.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.accept_applicant_documents.system.enums.StatusesOfDocuments;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.model.Document;
import ru.accept_applicant_documents.system.model.ExamResult;
import ru.accept_applicant_documents.system.repository.AdminRepo;
import ru.accept_applicant_documents.system.repository.ApplicantRepo;
import ru.accept_applicant_documents.system.repository.DocumentRepo;
import ru.accept_applicant_documents.system.repository.ExamResultRepo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicantServiceTest {
    @InjectMocks
    private ApplicantService applicantService;
    @Mock
    private ApplicantRepo applicantRepo;
    @Mock
    private ExamResultRepo examResultRepo;
    @Mock
    private DocumentRepo documentRepo;
    @InjectMocks
    private AdminService adminService;
    @Mock
    private AdminRepo adminRepo;

    @Test
    void applicantAccountAlreadyExist_returnTrue() {
        String existingEmail = "existing@mail.ru";
        when(applicantRepo.findByEmail(existingEmail)).thenReturn(Optional.of(new Applicant()));
        boolean result = applicantService.ApplicantAccountAlreadyExist(existingEmail);
        assertTrue(result);
    }

    @Test
    void setDocStatusByEmail_whenApplicantNotFound() {
        String nonExistingEmail = "nonexisting@mail.ru";
        StatusesOfDocuments newStatus = StatusesOfDocuments.VERIFIED;
        when(applicantRepo.findByEmail(nonExistingEmail))
                .thenReturn(Optional.empty());
        applicantService.setDocStatusByEmail(newStatus, nonExistingEmail);
        verify(applicantRepo, never()).setDocStatus(any(), anyString());
    }

    @Test
    void findApplicantByEmail_returnApplicant() {
        String email = "test@mail.ru";
        Applicant expected = new Applicant();
        when(applicantRepo.findByEmail(email)).thenReturn(Optional.of(expected));
        Optional<Applicant> result = applicantService.findByEmail(email);
        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    @Test
    void addApplicantExamResult_success() {
        ExamResult examResult = new ExamResult();
        when(examResultRepo.save(any(ExamResult.class))).thenReturn(examResult);
        ExamResult result = applicantService.addApplicantExamResult(examResult);
        assertNotNull(result);
        verify(examResultRepo, times(1)).save(examResult);
    }

    @Test
    void addDocument_success() {
        Document document = new Document();
        when(documentRepo.save(any(Document.class))).thenReturn(document);
        Document result = applicantService.addDocument(document);
        assertNotNull(result);
        verify(documentRepo, times(1)).save(document);
    }

    @Test
    void findAllByDocStatus_returnsList() {
        StatusesOfDocuments status = StatusesOfDocuments.VERIFIED;
        List<Applicant> expected = Arrays.asList(new Applicant(), new Applicant());
        when(applicantRepo.findAllByDocStatus(status)).thenReturn(expected);
        List<Applicant> result = applicantService.findAllByDocStatus(status);
        assertEquals(expected.size(), result.size());
        verify(applicantRepo, times(1)).findAllByDocStatus(status);
    }

    @Test
    void findExamsApplicantByApplicant_returnsResults() {
        Applicant applicant = new Applicant();
        List<ExamResult> expected = Arrays.asList(new ExamResult(), new ExamResult());
        when(examResultRepo.findAllByApplicant(applicant)).thenReturn(expected);
        List<ExamResult> result = applicantService.findExamsApplicantByApplicant(applicant);
        assertEquals(expected.size(), result.size());
        verify(examResultRepo, times(1)).findAllByApplicant(applicant);
    }

    @Test
    void findById_returnsApplicant() {
        Long id = 1L;
        Applicant expected = new Applicant();
        when(applicantRepo.findById(id)).thenReturn(Optional.of(expected));
        Optional<Applicant> result = applicantService.findById(id);
        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    @Test
    void adminAccountAlreadyExist_ReturnResult() {
        String nonExistingEmail = "nonexisting@mail.ru";
        when(adminRepo.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());
        boolean result = adminService.AdminAccountAlreadyExist(nonExistingEmail);
        assertFalse(result);
    }

    @Test
    void findAllByDocStatus_WhenStatusIncorrect() {
        StatusesOfDocuments status = StatusesOfDocuments.INCORRECT;
        Applicant incorrectApplicant = new Applicant();

        incorrectApplicant.setDocStatus(status);
        Applicant correctApplicant = new Applicant();

        correctApplicant.setDocStatus(StatusesOfDocuments.VERIFIED);
        when(applicantRepo.findAllByDocStatus(status))
                .thenReturn(List.of(incorrectApplicant));
        List<Applicant> result = applicantService.findAllByDocStatus(status);

        assertEquals(1, result.size());
        assertEquals(status, result.get(0).getDocStatus());
        verify(applicantRepo, times(1)).findAllByDocStatus(status);
    }
}
