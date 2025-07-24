package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Holiday; // Holidayをインポート
import com.example.demo.entity.User;
import com.example.demo.repository.HolidayRepository; // HolidayRepositoryをインポート
import com.example.demo.repository.NuserRepository;
import com.example.demo.service.Password_Hasher;

@Controller
public class AddController {

    @Autowired
    private NuserRepository nuserRepository;

    @Autowired
    private HolidayRepository holidayRepository; // HolidayRepositoryの追加

    // アカウント登録フォームを表示するメソッド
    @GetMapping("/Register")
    public String showRegistrationForm(Model model) {
        return "registration"; // registration.html の Thymeleaf テンプレートを返す
    }

    // アカウント登録処理
    @PostMapping("/Register")
    public String registerUser(
            @RequestParam String employeeId,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String department,
            @RequestParam int role,
            Model model) {

        // パスワードをハッシュ化（SHA-256など）する処理
        String hashedPassword = Password_Hasher.hashPassword(password);
        int departmentId = Integer.parseInt(department);

        // ユーザーオブジェクトを作成
        User newUser = new User();
        newUser.setEmployeeId(employeeId);
        newUser.setName(username);
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);
        newUser.setDepartmentId(departmentId);
        newUser.setRole(role);

        // ユーザーをデータベースに保存
        nuserRepository.save(newUser);

        // Holidayオブジェクトを作成し、データベースに保存
        Holiday newHoliday = new Holiday();
        newHoliday.setEmployeeId(employeeId);
        newHoliday.setPaid(20); // paidカラムに20を設定
        newHoliday.setSubstitute(0); // substituteカラムに0を設定

        // Holidayデータを保存
        holidayRepository.save(newHoliday);

        // 登録完了メッセージなどを表示
        model.addAttribute("successMessage", "アカウントが登録されました。");

        return "Admin"; // 再度登録画面やダッシュボードにリダイレクト
    }
}