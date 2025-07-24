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

    // アカウント登録フォームを表示するメソッド
    @GetMapping("/EmpEdit")
    public String showRegistrationForm(Model model) {
        return "EmpEdit"; // registration.html の Thymeleaf テンプレートを返す
    }

    // アカウント登録処理
    @PostMapping("/EmpEdit")
    public String registerUser(@RequestParam String username,@RequestParam String email,
                               @RequestParam String department, @RequestParam int role,
                               Model model,HttpSession empId) {
        
        int departmentId = Integer.parseInt(department);
        String employeeId = (String) empId.getAttribute("employeeId");
        // ユーザーオブジェクトを作成
        User newUser = new User();
        newUser.setEmployeeId(employeeId);
        newUser.setName(username);
        newUser.setEmail(email);
        newUser.setDepartmentId(departmentId);
        newUser.setRole(role);

        // ユーザーをデータベースに保存
        nuserRepository.save(newUser);

        // 登録完了メッセージなどを表示
        model.addAttribute("successMessage", "アカウントが編集されました。");

        return "Admin"; // 再度登録画面を表示（必要に応じてリダイレクトも可）
    }
}
