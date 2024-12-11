package ru.accept_applicant_documents.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.accept_applicant_documents.system.service.ApplicantService;

@Controller
public class AdminController {

    @Autowired
    ApplicantService applicantService;



}
