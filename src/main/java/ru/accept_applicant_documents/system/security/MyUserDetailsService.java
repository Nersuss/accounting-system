package ru.accept_applicant_documents.system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.accept_applicant_documents.system.model.Admin;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.repository.AdminRepo;
import ru.accept_applicant_documents.system.repository.ApplicantRepo;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    ApplicantRepo applicantRepo;
    @Autowired
    AdminRepo adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Applicant> applicantOptional = applicantRepo.findByEmail(username);
        if (applicantOptional.isPresent()) {
            return new MyUserDetails(applicantOptional.get());
        }
        Optional<Admin> adminOptional = adminRepo.findByEmail(username);
        if (adminOptional.isPresent()) {
            return new MyUserDetails(adminOptional.get());
        }
        throw new UsernameNotFoundException("Пользователя с такой почтой не существует");
    }
}
