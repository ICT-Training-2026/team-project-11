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
import com.example.demo.entity.Holiday;
import com.example.demo.form.AttendancetForm;
import com.example.demo.repository.AttendanceDeleteRepository;
import com.example.demo.repository.GetAttendanceRepository;
import com.example.demo.repository.HolidaymathRepository;
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
    private GetAttendanceRepository getattendancerepository;

    @Autowired
    private AttendanceService attendanceService;
    
    @Autowired
    private AttendanceDeleteRepository attendanceDeleteRepository;
    
    @Autowired
    private HolidaymathRepository holidaymathRepository;

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
        	Holiday holiday = holidaymathRepository.findByEmployeeId(employeeId);
        	if(holiday.getPaid()<=0) {
        		model.addAttribute("alertMessage", "残有給日数が0です。入力しなおしてください");
            	return "alertBack";
        	}
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
        	Holiday holiday = holidaymathRepository.findByEmployeeId(employeeId);
        	if(holiday.getSubstitute()<=0) {
        		model.addAttribute("alertMessage", "残振休日数が0です。入力しなおしてください");
            	return "alertBack";
        	}
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
        // worktimeが0分の場合のチェック
        if (form.getCheckInTime().equals(form.getCheckOutTime())) {
        	model.addAttribute("alertMessage", "勤怠時間が0分です。正しい時間を入力してください。");
            return "alertWorkTime"; // worktimeが0分の場合
        }
        // 未来の日付のチェック
        LocalDate currentDate = LocalDate.now();
        if (form.getWorkDate().isAfter(currentDate)) {
            // 年休、振休、休日の場合のみ許可
            if (!("年休".equals(form.getLeaveType()) || "振休".equals(form.getLeaveType()) || "休日".equals(form.getLeaveType()))) {
            	model.addAttribute("alertMessage", "日付確認をお願いします");
                return "alertFutureClock"; // 未来の日付で年休、振休、休日以外の場合
            }
        }
       
        // チェックイン時間とチェックアウト時間の範囲チェック
        LocalTime checkInTime = form.getCheckInTime();
        LocalTime checkOutTime = form.getCheckOutTime();
        LocalTime startTime = LocalTime.of(8, 0); // 8:00
        LocalTime endTime = LocalTime.of(22, 45); // 22:45
        // チェックインとチェックアウトの範囲チェック
        if (checkInTime.isBefore(startTime) || checkInTime.isAfter(endTime) ||
            checkOutTime.isBefore(startTime) || checkOutTime.isAfter(endTime)) {
            model.addAttribute("alertMessage", "勤怠は8:00～22:45の間で登録してください");
            return "alertWorkTime"; // チェックインまたはチェックアウトが範囲外の場合
        }
       
        // worktimeとbreaktimeの比較      
        LocalTime breaktime = form.getBreakTime();
        if (breaktime.isAfter(worktime)) {
        	model.addAttribute("alertMessage", "休憩時間が勤務時間を超過しています");
            return "alertAttendanceTime"; // breaktimeがworktimeより長い場合
        }
        
        // チェックイン時間がチェックアウト時間より遅い場合のチェック
        if (checkInTime.isAfter(checkOutTime)) {
            model.addAttribute("alertMessage", "勤怠を正しく登録してください");
            return "alertWorkTime"; // チェックインがチェックアウトより遅い場合
        }
        
        // worktimeが4時間以上でbreaktimeが1時間未満のチェック
        LocalTime minBreakTime = LocalTime.of(1,0); // 1時間
        LocalTime fourHours = LocalTime.of(4,0); // 4時間

        if (worktime.isAfter(fourHours) && breaktime.isBefore(minBreakTime)) {
        	model.addAttribute("alertMessage", "1時間以上の休憩を登録してください");
            return "alertBreakTime"; // worktimeが4時間以上でbreaktimeが1時間未満の場合
        }
        // 前日チェック
        LocalDate previousDate = form.getWorkDate().minusDays(1);
        AttendanceEntity previousAttendance = attendanceService.getPreviousAttendance(employeeId, previousDate);
         
        if (previousAttendance == null) {
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
        AttendanceEntity previousattendance =getattendancerepository.findByEmpIdAndWorkDate(employeeId, previousDate);
        if(previousattendance==null) {
        	LocalDate date=form.getWorkDate();
        	String dateString = date.toString();
        
            session.setAttribute("restartchecker", null);
            
        	session.setAttribute("restartchecker", dateString);

        	return "alertIf";
        }
        
        return "Register_complete";
    }

    @GetMapping("/Attendance_register")
    public String showRegisterForm(Model model, HttpSession session) {
        String employeeId = (String) session.getAttribute("employeeId");
        String workDate = (String) session.getAttribute("restartchecker");
        if (workDate != null) {
            
        	int empId = Integer.parseInt(employeeId);
        	LocalDate date = LocalDate.parse(workDate); // workDateをLocalDateに変換
        	attendanceDeleteRepository.deleteByEmployeeIdAndWorkdate(empId, date); // レコード削除
        	
        	
        }
        session.setAttribute("restartchecker", null);
       
        model.addAttribute("employeeId", employeeId);
        model.addAttribute("AttendancetForm", new AttendancetForm());
        return "Attendance_register";
    }
}

