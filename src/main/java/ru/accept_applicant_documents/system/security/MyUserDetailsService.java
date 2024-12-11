package ru.accept_applicant_documents.system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.repository.ApplicantRepo;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    ApplicantRepo applicantRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<Applicant> userOpt = applicantRepo.findByEmail(username);

        if (userOpt.isPresent()) {
            return new MyUserDetails(userOpt.get());
        }

        throw new UsernameNotFoundException("User with that email not found");
    }
}
