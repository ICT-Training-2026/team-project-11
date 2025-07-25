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
    @GetMapping("/Admin")
    public String Admin() {
        return "Admin";
    }
    // アカウント登録フォームを表示
  
    @GetMapping("/Register")
    public String showRegistrationForm(Model model) {
    	return "admin_add"; // admin_add.html にファイル名を一致させる
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

        // ① 重複チェック：既に社員IDが存在している場合
    	if (nuserRepository.existsByEmployeeId(employeeId)) {
    	    model.addAttribute("errorMessage", "この社員IDはすでに登録されています。");
    	      return "admin_add";
    	}

        // ② パスワードハッシュ化
        String hashedPassword = Password_Hasher.hashPassword(password);
        int departmentId = Integer.parseInt(department);

        // ③ Userオブジェクトの生成
        User newUser = new User();
        newUser.setEmployeeId(employeeId);
        newUser.setName(username);
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);
        newUser.setDepartmentId(departmentId);
        newUser.setRole(role);

        // ④ ユーザー登録
        nuserRepository.save(newUser);

        // ⑤ 有給初期化
        Holiday newHoliday = new Holiday();
        newHoliday.setEmployeeId(employeeId);
        newHoliday.setPaid(20);        // 有給：20日
        newHoliday.setSubstitute(0);   // 代休：0日

        holidayRepository.save(newHoliday);

        // ⑥ 完了メッセージ
        model.addAttribute("successMessage", "アカウントが登録されました。");

        return "redirect:/Admin"; // 管理者画面に遷移
    }
}
