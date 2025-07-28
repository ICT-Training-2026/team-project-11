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

    	
    	
    	// サービスから結果を受け取る（成功したかどうかを受け取る）
        String result = passwordService.updatePassword(employeeId, oldpw, newpw, new1pw);

        if ("mismatch".equals(result)) {
            model.addAttribute("error", "新しいパスワードが一致しません。");
            return "pw"; // 同じ画面に戻る
        }

        if ("invalid_old".equals(result)) {
            model.addAttribute("error", "現在のパスワードが正しくありません。");
            return "pw";
        }

        
        
        return passwordService.updatePassword(employeeId, oldpw, newpw, new1pw);
    }
}