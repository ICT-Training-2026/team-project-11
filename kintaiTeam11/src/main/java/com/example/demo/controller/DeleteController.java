package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Holiday;
import com.example.demo.entity.User;
import com.example.demo.repository.AttendanceDeleteRepository;
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
    
    @Autowired
    private AttendanceDeleteRepository attendanceDeleteRepository;
    
   
    // ユーザーを削除するエンドポイント
    @DeleteMapping("/users/{employeeId}")
    public String deleteUser(@PathVariable String employeeId, HttpSession session,Model model) {
    	String checklog = (String) session.getAttribute("employeeId");
    	if(checklog ==null) {
    		 model.addAttribute("alertMessage", "セッションが無効です。再度ログインしてください。");
             return "alertTop";
    	}
        User user = userRepository.findByEmployeeId(employeeId);
        int empId = Integer.parseInt(employeeId);
        Holiday holiday=holidayRepository.findByEmployeeId(empId);
        
       
        if (user != null) {
        	
        	if(holiday != null) {
        	deleteholidayRepository.delete(holiday);
        	}
        	attendanceDeleteRepository.deleteByEmployeeId(empId);
        	
        	deleteRepository.delete(user);
        	
             // ユーザーを削除
            return "ユーザーが削除されました: " + employeeId;
        } else {
            return "ユーザーが見つかりません: " + employeeId;
        }
    }
}