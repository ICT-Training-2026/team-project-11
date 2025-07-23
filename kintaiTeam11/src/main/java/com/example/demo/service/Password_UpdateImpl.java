package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.At1;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Password_UpdateImpl implements Password_Update{

    @Autowired
    private final UserRepository userRepository;

    @Override
    public String updatePassword(String employeeId, String oldPassword, String newPassword, String confirmPassword) {
        // 新しいパスワードが一致するか確認
        if (!newPassword.equals(confirmPassword)) {
            return "/top";
        }

        // 旧パスワードをハッシュ化
        String hashedOldPassword = Password_Hasher.hashPassword(oldPassword);

        // ユーザーを検索
        At1 employee = userRepository.findByEmployeeIdAndPassword(employeeId, hashedOldPassword);

        // ユーザーが見つからない場合
        if (employee == null) {
            return "/pw";
        }

        // 新しいパスワードをハッシュ化して保存
        String hashedNewPassword = Password_Hasher.hashPassword(newPassword);
        employee.setPassword(hashedNewPassword);
        userRepository.save(employee);

        return "Pw_complete";
    }
}