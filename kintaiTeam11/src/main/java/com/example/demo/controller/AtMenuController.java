package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AtMenuController {

    @GetMapping("/AtMenu")
    public String showAtMenu(HttpSession session,Model model) {
    	String checklog = (String) session.getAttribute("employeeId");
    	if(checklog ==null) {
    		 model.addAttribute("alertMessage", "セッションが無効です。再度ログインしてください。");
             return "alertTop";
    	}
        return "AtMenu"; // ← templates/AtMenu.html に対応
    }
}
