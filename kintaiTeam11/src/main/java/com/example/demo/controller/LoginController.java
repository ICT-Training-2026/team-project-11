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
    private UserRepository userRepository; // ユーザー情報を取得するためのリポジトリ

    @PostMapping("/System")
    public String login(@RequestParam String employeeId, @RequestParam String password, Model model,HttpSession session) {
    	 String hashedPassword = Password_Hasher.hashPassword(password);
        // データベースで社員番号とパスワードを照合
        At1 user = userRepository.findByEmployeeIdAndPassword(employeeId, hashedPassword);

        if (user != null) {
        	session.setAttribute("employeeId", employeeId);
            // 一致した場合、システムメニュー画面に遷移
            return "System"; // Thymeleafは templates/System.html を探します
        } else {
            // 一致しない場合、エラーメッセージを表示
            model.addAttribute("errorMessage", "社員番号またはパスワードが間違っています。");
            return "L"; // ログイン画面に戻る
        }
    }
}
