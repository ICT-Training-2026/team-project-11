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
public class EmpEditController {

    @Autowired
    private NuserRepository nuserRepository;

    // 編集画面の表示
    @GetMapping("/EmpEdit")
    public String showEditForm(@RequestParam("employeeId") String employeeId, Model model, HttpSession session) {
        User user = nuserRepository.findByEmployeeId(employeeId);
        if (user == null) {
            model.addAttribute("errorMessage", "社員が見つかりません。");
            return "Admin_Search";
        }

        session.setAttribute("employeeId", employeeId); // セッションに保存しておく
        model.addAttribute("user", user);
        return "EmpEdit";
    }

    // 編集処理（空欄以外を反映）
    @PostMapping("/EmpEdit")
    public String updateUser(
        @RequestParam(required = false) String username,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String department,
        @RequestParam(required = false) String role,
        Model model,
        HttpSession session
    ) {
        String employeeId = (String) session.getAttribute("employeeId");
        if (employeeId == null) {
            model.addAttribute("errorMessage", "編集対象の社員が見つかりません。");
            return "EmpEdit";
        }

        User user = nuserRepository.findByEmployeeId(employeeId);
        if (user == null) {
            model.addAttribute("errorMessage", "社員が見つかりません。");
            return "EmpEdit";
        }

        // 入力があるものだけ更新
        if (username != null && !username.isBlank()) {
            user.setName(username);
        }
        if (email != null && !email.isBlank()) {
            user.setEmail(email);
        }
        if (department != null && !department.isBlank()) {
            user.setDepartmentId(Integer.parseInt(department));
        }
        if (role != null && !role.isBlank()) {
            user.setRole(Integer.parseInt(role));
        }

        nuserRepository.save(user);

        model.addAttribute("successMessage", "変更を保存しました。");
        model.addAttribute("user", user); // 更新後の情報を再表示

        return "EmpEdit";
    }
}
