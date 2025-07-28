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
import com.example.demo.repository.GetAttendanceRepository;
import com.example.demo.service.AttendanceService;
import com.example.demo.service.EditService;
import com.example.demo.service.HolidaymathService;
import com.example.demo.service.LeaveTypeService;
import com.example.demo.service.TimeService;
@Controller
public class AttendanceEditController {
	@Autowired
	private EditService service;
	@Autowired
	private TimeService Tservice;
	
	@Autowired
	private AttendanceService attendanceService;
	
	@Autowired
	private LeaveTypeService leavetypeservice;
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
            BindingResult result,HttpSession session,Model model) {
    	
    	
    	
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
        String empId = (String) session.getAttribute("employeeId");
        int employeeId = Integer.parseInt(empId);
        AttendanceEntity attendance =getattendancerepository.findByEmpIdAndWorkDate(employeeId, form.getWorkDate());
        int type =leavetypeservice.leaveTypeChecker(attendance.getLeaveType(), form.getLeaveType());
        holidaymathService.incrementdecrement(type,employeeId);
        
      //  int empId = Integer.parseInt(employeeId);
        
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
        	e.setConsecutiveDays(1);
        	e.setRemarks(form.getRemarks());
            e.setApproval(0);
            e.setUpdatedAt(currentDateTime);
        	
        }
        else if("振休".equals(form.getLeaveType())||"振休".equals(form.getLeaveType())) {
        	e.setEmpId(employeeId);
            e.setWorkDate(form.getWorkDate());
            e.setLeaveType(form.getLeaveType());
        	e.setCheckInTime(LocalTime.of(0, 0));
        	e.setCheckOutTime(LocalTime.of(0, 0));
        	e.setOvertimeHours(LocalTime.of(0, 0));
        	e.setWorkTimeHours(LocalTime.of(0, 0));
        	e.setBreakTime(LocalTime.of(0, 0));
        	e.setConsecutiveDays(1);
        	e.setRemarks(form.getRemarks());
            e.setApproval(0);
            e.setUpdatedAt(currentDateTime);
        }else {
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
        }
        LocalDate previousDate = form.getWorkDate().minusDays(1);
        // 前日の勤怠データを取得
        AttendanceEntity previousAttendance = attendanceService.getPreviousAttendance(employeeId, previousDate);

        // 前日の勤怠データが存在しない場合
        if (previousAttendance == null) {
            // 警告メッセージをモデルに追加
            model.addAttribute("warningMessage", "前日の勤怠が登録されていませんが進みますか？");
            model.addAttribute("showWarning", true); // 警告メッセージを表示するフラグ
            // LeaveTypeが「出勤」または「振出」の場合にデータベースを更新
            if ("出勤".equals(form.getLeaveType()) || "振出".equals(form.getLeaveType())) {
                e.setConsecutiveDays(1);
            }else {
            	e.setConsecutiveDays(0);
            }
        }else {
            // LeaveTypeが「出勤」または「振出」の場合にデータベースを更新
            if ("出勤".equals(form.getLeaveType()) || "振出".equals(form.getLeaveType())) {
                // 前日の連続勤務日数を取得し、今日の連続勤務日数を設定
                int previousConsecutiveDays = previousAttendance.getConsecutiveDays();
                e.setConsecutiveDays(previousConsecutiveDays + 1);
            }else {
            	e.setConsecutiveDays(0);
            }
        }
        service.regist(e);
        return "Edit_complete";
    }
    @GetMapping("/Attendance_Edit")
    public String showRegisterForm(Model model,HttpSession session,Model model2) {
    	String employeeId = (String) session.getAttribute("employeeId");
    	model.addAttribute("empl",employeeId);
        model.addAttribute("AttendancetForm", new AttendancetForm()); // 必須！
        return "Attendance_edit"; // HTML名
    }
}











