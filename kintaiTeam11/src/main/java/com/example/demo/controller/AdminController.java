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
    @GetMapping("/checkRole") // 役割をチェックするためのエンドポイント
    public String checkRole(Model model, HttpSession session) {
        // セッションから社員IDを取得
        String employeeId = (String) session.getAttribute("employeeId");

        if (employeeId != null) {
            // データベースで社員IDを参照
            AtAd user = adminRepository.findByEmployeeId(employeeId);

            if (user != null && user.getRole() == 1) { // roleが1を確認（管理者）
                // 管理者の場合、AdminのURLにリダイレクト
                return "redirect:/Admin"; // 管理者画面にリダイレクト
            } 
            else if (user != null && user.getDEP_ID() == 3) { // DEP_IDが3を確認（総務）
                // 管理者の場合、AdminのURLにリダイレクト
                return "redirect:/Admin"; // 管理者画面にリダイレクト
            }else {
                // 管理者でない場合、エラーメッセージを表示
                model.addAttribute("errorMessage", "権限がありません。");
                return "Atmenu"; // アクセス拒否ページまたはメニュー画面に戻る
            }
        } else {
            // セッションが無効な場合
            model.addAttribute("errorMessage", "セッションが無効です。再度ログインしてください。");
            return "L"; // ログインページにリダイレクト
        }
    }
}