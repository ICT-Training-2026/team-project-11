package com.example.demo.controller;

import java.time.Duration;
import java.time.LocalDate;
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
import com.example.demo.service.AttendanceService;
import com.example.demo.service.HolidaymathService;
import com.example.demo.service.RegistService;
import com.example.demo.service.TimeService;

@Controller
public class RegisterController {

    @Autowired
    private RegistService service;

    @Autowired
    private TimeService Tservice;
    
    @Autowired
    private HolidaymathService holidaymathService;


    @Autowired
    private AttendanceService attendanceService;

    @ModelAttribute("AttendancetForm")
    public AttendancetForm form() {
        return new AttendancetForm();
    }

    @PostMapping("/Register_complate")
    public String registerComplate(
            @Validated @ModelAttribute AttendancetForm form,
            BindingResult result,
            HttpSession session,
            Model model) {
    	String checklog = (String) session.getAttribute("employeeId");
    	if(checklog ==null) {
    		 model.addAttribute("alertMessage", "セッションが無効です。再度ログインしてください。");
             return "alertTop";
    	}

        if (result.hasErrors()) {
            return "Attendance_register";
        }
        
        

        String empId = (String) session.getAttribute("employeeId");
        int employeeId = Integer.parseInt(empId);

        // ✅ 重複チェック
        if (attendanceService.isAlreadyRegistered(employeeId, form.getWorkDate())) {
            model.addAttribute("duplicateError", "この日付の勤怠はすでに登録されています。");
            return "Attendance_register";
        }
        LocalTime thresholdTime = LocalTime.of(4, 0); // 04:00を設定

        if ((form.getBreakTime()).isAfter(thresholdTime)) {
        	model.addAttribute("alertMessage", "休憩時間が長すぎます。入力しなおしてください");
        	return "alertBack";
        }
        LocalTime base = LocalTime.of(0, 0);
        Duration overT = Tservice.timediff(form.getCheckInTime(), form.getCheckOutTime(), form.getBreakTime());
        LocalTime overtime = base.plus(overT);
        Duration workT = Tservice.timediff(form.getCheckInTime(), form.getCheckOutTime(), form.getBreakTime(), overtime);
        LocalTime worktime = base.plus(workT);
        LocalDateTime currentDateTime = LocalDateTime.now();

        AttendanceEntity e = new AttendanceEntity();
        if("年休".equals(form.getLeaveType())) {
        	e.setEmpId(employeeId);
            e.setWorkDate(form.getWorkDate());
            e.setLeaveType(form.getLeaveType());
        	e.setCheckInTime(LocalTime.of(0, 0));
        	e.setCheckOutTime(LocalTime.of(0, 0));
        	e.setOvertimeHours(LocalTime.of(0, 0));
        	e.setWorkTimeHours(LocalTime.of(7, 0));
        	e.setBreakTime(LocalTime.of(0, 0));
        	e.setRemarks(form.getRemarks());
            e.setApproval(0);
            e.setUpdatedAt(currentDateTime);
        	holidaymathService.incrementdecrement(4,employeeId);
        }
        else if("振休".equals(form.getLeaveType())) {
        	e.setEmpId(employeeId);
            e.setWorkDate(form.getWorkDate());
            e.setLeaveType(form.getLeaveType());
        	e.setCheckInTime(LocalTime.of(0, 0));
        	e.setCheckOutTime(LocalTime.of(0, 0));
        	e.setOvertimeHours(LocalTime.of(0, 0));
        	e.setWorkTimeHours(LocalTime.of(0, 0));
        	e.setBreakTime(LocalTime.of(0, 0));
        	e.setRemarks(form.getRemarks());
            e.setApproval(0);
            e.setUpdatedAt(currentDateTime);
            holidaymathService.incrementdecrement(5,employeeId);
        }else if("休日".equals(form.getLeaveType())) {
        	e.setEmpId(employeeId);
            e.setWorkDate(form.getWorkDate());
            e.setLeaveType(form.getLeaveType());
        	e.setCheckInTime(LocalTime.of(0, 0));
        	e.setCheckOutTime(LocalTime.of(0, 0));
        	e.setOvertimeHours(LocalTime.of(0, 0));
        	e.setWorkTimeHours(LocalTime.of(0, 0));
        	e.setBreakTime(LocalTime.of(0, 0));
        	e.setRemarks(form.getRemarks());
            e.setApproval(0);
            e.setUpdatedAt(currentDateTime);
        }else if("振出".equals(form.getLeaveType())) {
        	e.setEmpId(employeeId);
            e.setWorkDate(form.getWorkDate());
            e.setLeaveType(form.getLeaveType());
            e.setCheckInTime(form.getCheckInTime());
            e.setCheckOutTime(form.getCheckOutTime());
            e.setBreakTime(form.getBreakTime());
            e.setOvertimeHours(overtime);
            e.setWorkTimeHours(worktime);
            e.setRemarks(form.getRemarks());
            e.setApproval(0);
            e.setUpdatedAt(currentDateTime);
            holidaymathService.incrementdecrement(11,employeeId);
        }
        else {
        e.setEmpId(employeeId);
        e.setWorkDate(form.getWorkDate());
        e.setLeaveType(form.getLeaveType());
        e.setCheckInTime(form.getCheckInTime());
        e.setCheckOutTime(form.getCheckOutTime());
        e.setBreakTime(form.getBreakTime());
        e.setOvertimeHours(overtime);
        e.setWorkTimeHours(worktime);
        e.setRemarks(form.getRemarks());
        e.setApproval(0);
        e.setUpdatedAt(currentDateTime);
        }

        // 前日チェック
        LocalDate previousDate = form.getWorkDate().minusDays(1);
        AttendanceEntity previousAttendance = attendanceService.getPreviousAttendance(employeeId, previousDate);

        if (previousAttendance == null) {
            model.addAttribute("warningMessage", "前日の勤怠が登録されていませんが進みますか？");
            model.addAttribute("showWarning", true);
            if ("出勤".equals(form.getLeaveType()) || "振出".equals(form.getLeaveType())) {
                e.setConsecutiveDays(1);
            } else {
                e.setConsecutiveDays(0);
            }
        } else {
            if ("出勤".equals(form.getLeaveType()) || "振出".equals(form.getLeaveType())) {
                int previousConsecutiveDays = previousAttendance.getConsecutiveDays();
                e.setConsecutiveDays(previousConsecutiveDays + 1);
            } else {
                e.setConsecutiveDays(0);
            }
        }


        service.regist(e);
        return "Register_complete";
    }

    @GetMapping("/Attendance_register")
    public String showRegisterForm(Model model, HttpSession session) {
        String employeeId = (String) session.getAttribute("employeeId");
        model.addAttribute("employeeId", employeeId);
        model.addAttribute("AttendancetForm", new AttendancetForm());
        return "Attendance_register";
    }
}

