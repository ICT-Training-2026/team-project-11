package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Holiday;
import com.example.demo.entity.User;
import com.example.demo.repository.HolidayRepository;
import com.example.demo.repository.NuserRepository;
import com.example.demo.service.Password_Hasher;
@Controller
public class AddController {
    @Autowired
    private NuserRepository nuserRepository;
    @Autowired
    private HolidayRepository holidayRepository;
    // 管理者画面に遷移
    @GetMapping("/Admin")
    public String Admin() {
        return "Admin";
    }
    // ユーザー登録フォーム表示
    @GetMapping("/Register")
    public String showRegistrationForm(Model model) {
        return "admin_add"; // 登録画面(admin_add.html)
    }
    // ユーザー登録処理
    @PostMapping("/Register")
    public String registerUser(
            @RequestParam String employeeId,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String department,
            @RequestParam int role,
            Model model) {
        // ① 重複チェック
        if (nuserRepository.existsByEmployeeId(employeeId)) {
            model.addAttribute("duplicateId", true); // JavaScript用フラグ
            return "admin_add"; // 再表示
        }
        // ② パスワードハッシュ化
        String hashedPassword = Password_Hasher.hashPassword(password);
        int departmentId = Integer.parseInt(department);
        // ③ ユーザーエンティティ作成
        User newUser = new User();
        newUser.setEmployeeId(employeeId);
        newUser.setName(username);
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);
        newUser.setDepartmentId(departmentId);
        newUser.setRole(role);
        // ④ 登録
        nuserRepository.save(newUser);
        // ⑤ 有給情報も登録
        int empId = Integer.parseInt(employeeId);
        Holiday holiday = new Holiday();
        holiday.setEmployeeId(empId);
        holiday.setPaid(20);
        holiday.setSubstitute(0);
        holidayRepository.save(holiday);
        // ⑥ 管理者画面へ遷移
        return "redirect:/Admin";
    }
}














