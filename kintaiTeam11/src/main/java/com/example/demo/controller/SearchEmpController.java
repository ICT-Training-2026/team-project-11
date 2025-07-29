package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.NuserRepository;

@Controller
public class SearchEmpController {

    @Autowired
    private NuserRepository nuserRepository;

    // 検索フォームの表示
    @GetMapping("/EmpSearch")
    public String showSearchForm() {
        return "Admin_Search"; // ← HTMLファイル名に対応
    }

    // 社員ID検索処理
    @PostMapping("/EmpSearch")
    public String searchEmployee(@RequestParam String employeeId, Model model, HttpSession session) {
    	String checklog = (String) session.getAttribute("employeeId");
    	if(checklog ==null) {
    		 model.addAttribute("alertMessage", "セッションが無効です。再度ログインしてください。");
             return "alertTop";
    	}
        User user = nuserRepository.findByEmployeeId(employeeId);
        if (user != null) {
            model.addAttribute("resultUser", user);
        } else {
            model.addAttribute("notFoundMessage", "該当する社員が見つかりませんでした。");
        }
        return "Admin_Search"; // ← 検索結果も同じテンプレートに表示
    }
}
