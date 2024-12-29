package ru.accept_applicant_documents.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.accept_applicant_documents.system.model.Admin;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.repository.AdminRepo;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    AdminRepo adminRepo;

    public Optional<Admin> findByEmail(String email) {
        return adminRepo.findByEmail(email);
    }

    public boolean AdminAccountAlreadyExist(String email) {
        if (adminRepo.findByEmail(email).isEmpty()) {
            return false;
        }
        return true;
    }

}
