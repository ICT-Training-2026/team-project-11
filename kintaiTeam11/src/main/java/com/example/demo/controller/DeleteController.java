package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Holiday;
import com.example.demo.entity.User;
import com.example.demo.repository.DeleteRepository;
import com.example.demo.repository.DeleteholidayRepository;
import com.example.demo.repository.HolidayRepository;
import com.example.demo.repository.NuserRepository;

@RestController
public class DeleteController {

    @Autowired
    private NuserRepository userRepository;
    
    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private DeleteRepository deleteRepository;
    
    @Autowired
    private DeleteholidayRepository deleteholidayRepository;
    
    // ユーザーを削除するエンドポイント
    @DeleteMapping("/users/{employeeId}")
    public String deleteUser(@PathVariable String empId) {
        User user = userRepository.findByEmployeeId(empId);
        int employeeId = Integer.parseInt(empId);
        Holiday holiday=holidayRepository.findByEmployeeId(employeeId);
        if (user != null) {
        	deleteholidayRepository.delete(holiday);
        	
            deleteRepository.delete(user); // ユーザーを削除
            return "ユーザーが削除されました: " + employeeId;
        } else {
            return "ユーザーが見つかりません: " + employeeId;
        }
    }
}