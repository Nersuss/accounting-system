package ru.accept_applicant_documents.system.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import ru.accept_applicant_documents.system.dto.RegisterApplicantDto;
import ru.accept_applicant_documents.system.service.ApplicantService;

@Controller
public class RegistrationController {

    @Autowired
    ApplicantService applicantService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(WebRequest request, Model model) {
        RegisterApplicantDto registerApplicantDto = new RegisterApplicantDto();
        model.addAttribute("user", registerApplicantDto);
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(
            @ModelAttribute("user") @Valid RegisterApplicantDto registerApplicantDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (!applicantService.ApplicantAccountAlreadyExist(registerApplicantDto.getEmail()))
        {
            registerApplicantDto.setPassword(bCryptPasswordEncoder.encode(registerApplicantDto.getPassword()));
            applicantService.registerNewApplicantAccount(registerApplicantDto);
        } else {
            model.addAttribute("account", "Аккаунт с такой почтой уже существует.");
            return "register";
        }
        return "redirect:/login";
    }
}