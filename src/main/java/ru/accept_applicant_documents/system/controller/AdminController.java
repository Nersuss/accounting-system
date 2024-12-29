package ru.accept_applicant_documents.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.accept_applicant_documents.system.model.Admin;
import ru.accept_applicant_documents.system.model.Applicant;
import ru.accept_applicant_documents.system.service.AdminService;
import ru.accept_applicant_documents.system.service.ApplicantService;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/admin/lk")
    public String getAdminLk(Model model)
    {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Admin admin = adminService.findByEmail(email).get();
        model.addAttribute("admin", admin);
        return "admin-lk";
    }

}
