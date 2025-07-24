package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.NuserRepository;

@Controller
public class SearchEmpController {

    @Autowired
    private NuserRepository nuserRepository;

    @PostMapping("/SearchEmp_complete")
    public String searchEmployee(@RequestParam String employeeId, Model model) {
        // 社員番号に基づいてユーザーを検索
        User user = nuserRepository.findByEmployeeId(employeeId);

        // ユーザーが見つかったか確認
        if (user != null) {
            // 存在する場合、処理を続行
            // ここに続行するための処理を書く
            return "NextPage"; // 遷移先のテンプレート名
        } else {
            // 存在しない場合
            return "Admin_Search"; // Search_Empのテンプレート名
        }
    }
}
	 