package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.At1;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.Password_Hasher;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/System")
    public String login(@RequestParam String employeeId,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {

        // パスワードをハッシュ化して照合
        String hashedPassword = Password_Hasher.hashPassword(password);
        At1 user = userRepository.findByEmployeeIdAndPassword(employeeId, hashedPassword);

        if (user != null) {
            // セッションに社員番号を保存
            session.setAttribute("employeeId", employeeId);

            // ✅ 勤怠編集ではなく、システムメニューへ遷移
            return "System"; // ← templates/System.html に対応
        } else {
            model.addAttribute("errorMessage", "社員番号またはパスワードが間違っています。");
            return "L"; // ← ログイン画面（templates/L.html）
        }
    }
}
