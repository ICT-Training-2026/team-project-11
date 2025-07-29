package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.AtAd;
import com.example.demo.repository.AdminRepository;

@Controller
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    // ログイン後や、他のアクション後に役割を確認するメソッド
    @GetMapping("/checkRole")
    public String checkRole(Model model, HttpSession session) {
        String employeeId = (String) session.getAttribute("employeeId");

        if (employeeId != null) {
            AtAd user = adminRepository.findByEmployeeId(employeeId);

            if (user != null && (user.getRole() == 1 || user.getDEP_ID() == 3)) {
                return "redirect:/Admin";
            } else {
                model.addAttribute("alertMessage", "管理者権限がありません。");
                return "alertBack";
            }
        } else {
            model.addAttribute("alertMessage", "セッションが無効です。再度ログインしてください。");
            return "alertTop";
        }
    }

    // 他の /Admin 関連の処理などもこの中にまとめてOK
}