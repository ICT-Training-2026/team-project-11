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
import com.example.demo.service.RegistService;
import com.example.demo.service.TimeService;

@Controller
public class RegisterController{

    @Autowired
    private RegistService service;

    @Autowired private TimeService Tservice;

    @Autowired private AttendanceService attendanceService;

    @ModelAttribute("AttendancetForm")
    public AttendancetForm form() {
        return new AttendancetForm();
    }

    @PostMapping("/Register_complate")
    public String registerComplate(
    		@Validated @ModelAttribute AttendancetForm form,

            BindingResult result,HttpSession session, Model model) {


        if (result.hasErrors()) {
            System.out.println("error");
            return "Register_complate"; // HTMLのテンプレート名
        }

        LocalTime base = LocalTime.of(0, 0);
        
        Duration overT = Tservice.timediff(form.getCheckInTime(), form.getCheckOutTime(),form.getBreakTime());
        
        LocalTime overtime =base.plus(overT);
        
        Duration workT = Tservice.timediff(form.getCheckInTime(), form.getCheckOutTime(),form.getBreakTime(),overtime);
        
        LocalTime worktime =base.plus(workT);
        
        LocalDateTime currentDateTime = LocalDateTime.now();


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
        e.setWorkTimeHours(worktime);
        e.setRemarks(form.getRemarks());
        e.setApproval(0);
        e.setUpdatedAt(currentDateTime);


        // 1日前の日付を取得
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
//        System.out.println(form.getLeaveType());        // leaveTypeが1の場合に"出勤"を設定
//        if (form.getLeaveType() == "1") {
//            e.setLeaveType("出勤");
//        } 
//        if (form.getLeaveType() == "2") {
//            e.setLeaveType("振出");
//        } else {
//        	e.setLeaveType("カス");
//        }


        service.regist(e);
        return "Register_complete";
        
    }
  @GetMapping("/Attendance_register")
  public String showRegisterForm(Model model,Model model2,HttpSession session) {
  	String employeeId = (String) session.getAttribute("employeeId");
  	model2.addAttribute("employeeId",employeeId);
      model.addAttribute("AttendancetForm", new AttendancetForm()); // 必須！
      return "Attendance_register"; // HTML名
  }
}