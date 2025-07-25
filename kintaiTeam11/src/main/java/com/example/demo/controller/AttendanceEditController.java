package com.example.demo.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.AttendanceEntity;
import com.example.demo.form.AttendancetForm;
import com.example.demo.repository.GetAttendanceRepository;
import com.example.demo.service.EditService;
import com.example.demo.service.HolidaymathService;
import com.example.demo.service.TimeService;

@Controller
public class AttendanceEditController {

	@Autowired
	private EditService service;
	@Autowired
	private TimeService Tservice;
	
	@Autowired
    private GetAttendanceRepository getattendancerepository;
	
	@Autowired
    private HolidaymathService holidaymathService;

    @ModelAttribute("AttendancetForm")
    public AttendancetForm form() {
        return new AttendancetForm();
    }

    @PostMapping("/Edit_complete")
    public String AttendanceEditComplate(
    		@Validated @ModelAttribute AttendancetForm form,
            BindingResult result,HttpSession session) {

    	

    	
    	
        if (result.hasErrors()) {
        	System.out.println("error");
            return "Atmenu"; // HTMLのテンプレート名
       }
        
        LocalTime base = LocalTime.of(0, 0);
        
        Duration overT = Tservice.timediff(form.getCheckInTime(), form.getCheckOutTime(),form.getBreakTime());
        
        LocalTime overtime =base.plus(overT);
        
        Duration workT = Tservice.timediff(form.getCheckInTime(), form.getCheckOutTime(),form.getBreakTime(),overtime);
        
        LocalTime worktime =base.plus(workT);
        
        LocalDateTime currentDateTime = LocalDateTime.now();
        

        String employeeId = (String) session.getAttribute("employeeId");
      
        
        
        AttendanceEntity attendance =getattendancerepository.findByEmpIdAndWorkDate(employeeId, form.getWorkDate());
        if ("有給".equals(attendance.getLeaveType())) {
        	if("出勤".equals(form.getLeaveType())) {
        		holidaymathService.incrementPaid(employeeId);
        	}
        	else if("振出".equals(form.getLeaveType())) {
        		holidaymathService.incrementSubstitute(employeeId);
        	};
        }
        if ("出勤".equals(attendance.getLeaveType())) {
        	if("有給".equals(form.getLeaveType())) {
        		holidaymathService.decrementPaid(employeeId);
        	}
        	else if("振休".equals(form.getLeaveType())) {
        		holidaymathService.decrementSubstitute(employeeId);
        	};
        }
      //  int empId = Integer.parseInt(employeeId);

        String empId = (String) session.getAttribute("employeeId");
        
        int employeeId = Integer.parseInt(empId);

        AttendanceEntity e = new AttendanceEntity();
        e.setEmpId(employeeId);
        e.setWorkDate(form.getWorkDate());
        e.setLeaveType(form.getLeaveType());
        e.setCheckInTime(form.getCheckInTime());
        e.setCheckOutTime(form.getCheckOutTime());
        e.setBreakTime(form.getBreakTime());
        e.setOvertimeHours(overtime);
        e.setConsecutiveDays(1);
        e.setWorkTimeHours(worktime);
        e.setRemarks(form.getRemarks());
        e.setApproval(0);
        e.setUpdatedAt(currentDateTime);

        service.regist(e);
        return "Edit_complete";
    }
    
    @GetMapping("/Attendance_Edit")
    public String showRegisterForm(Model model,HttpSession session,Model model2) {
    	String employeeId = (String) session.getAttribute("employeeId");
    	model2.addAttribute("employeeId",employeeId);
        model.addAttribute("AttendancetForm", new AttendancetForm()); // 必須！
        return "Attendance_edit"; // HTML名
    }
}
