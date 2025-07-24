package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.DeleteRepository;
import com.example.demo.repository.NuserRepository;

@RestController
public class DeleteController {

    @Autowired
    private NuserRepository userRepository;

    @Autowired
    private DeleteRepository deleteRepository;
    // ユーザーを削除するエンドポイント
    @DeleteMapping("/users/{employeeId}")
    public String deleteUser(@PathVariable String employeeId) {
        User user = userRepository.findByEmployeeId(employeeId);
        if (user != null) {
            deleteRepository.delete(user); // ユーザーを削除
            return "ユーザーが削除されました: " + employeeId;
        } else {
            return "ユーザーが見つかりません: " + employeeId;
        }
    }
}