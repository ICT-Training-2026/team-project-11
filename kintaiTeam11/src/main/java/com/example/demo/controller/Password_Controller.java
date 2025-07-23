package com.example.demo.controller;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.Password_UpdateImpl;

@Controller
public class Password_Controller {

    private Password_UpdateImpl passwordService;

    public Password_Controller(Password_UpdateImpl passwordService) {
            this.passwordService = passwordService;
    }
    
    @PostMapping("/Register_pw")
    public String updatePassword(@RequestParam String oldpw, 
                                 @RequestParam String newpw, 
                                 @RequestParam String new1pw,
                                 Model model, HttpSession session) {
    	String employeeId = (String) session.getAttribute("employeeId");

        return passwordService.updatePassword(employeeId, oldpw, newpw, new1pw);
    }
}