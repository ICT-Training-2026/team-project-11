package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.entity.At1;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Password_UpdateImpl implements Password_Update {

    private final UserRepository userRepository;

    @Override
    public String updatePassword(String employeeId, String oldPassword, String newPassword, String confirmPassword) {
        // 新旧パスワードが一致しない場合
        if (!newPassword.equals(confirmPassword)) {
            return "mismatch";
        }

        // 旧パスワードをハッシュ化してユーザーを検索
        String hashedOldPassword = Password_Hasher.hashPassword(oldPassword);
        At1 employee = userRepository.findByEmployeeIdAndPassword(employeeId, hashedOldPassword);

        // ユーザーが見つからない場合（旧パスワードが間違っている）
        if (employee == null) {
            return "invalid_old";
        }

        // 新しいパスワードを保存（ハッシュ化）
        String hashedNewPassword = Password_Hasher.hashPassword(newPassword);
        employee.setPassword(hashedNewPassword);
        userRepository.save(employee);

        // 成功した場合
        return "success";
    }
}
