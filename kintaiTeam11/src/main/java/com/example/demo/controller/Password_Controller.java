package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.service.Password_UpdateImpl;

@Controller
public class Password_Controller {

    private final Password_UpdateImpl passwordService;

    public Password_Controller(Password_UpdateImpl passwordService) {
        this.passwordService = passwordService;
    }

    @PostMapping("/Register_pw")
    public String updatePassword(@RequestParam String oldpw, 
                                 @RequestParam String newpw, 
                                 @RequestParam String new1pw,
                                 Model model, HttpSession session) {

        String employeeId = (String) session.getAttribute("employeeId");

        // 1回だけ呼び出す！
        String result = passwordService.updatePassword(employeeId, oldpw, newpw, new1pw);

        switch (result) {
            case "mismatch":
                model.addAttribute("error", "新しいパスワードが一致しません。");
                return "pw";
            case "invalid_old":
                model.addAttribute("error", "現在のパスワードが正しくありません。");
                return "pw";
            case "success":
                model.addAttribute("success", true); // JSで処理されるフラグ
                return "pw";
            default:
                model.addAttribute("error", "予期せぬエラーが発生しました。");
                return "pw";
        }
    }
}
