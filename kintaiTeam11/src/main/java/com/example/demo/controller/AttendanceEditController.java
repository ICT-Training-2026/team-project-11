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
            BindingResult result,
            HttpSession session,
            Model model) {

        String empIdStr = (String) session.getAttribute("employeeId");
        if (empIdStr == null) {
            model.addAttribute("alertMessage", "セッションが無効です。再度ログインしてください。");
            return "alertTop";
        }

        if (result.hasErrors()) {
            return "Atmenu";
        }

        int employeeId = Integer.parseInt(empIdStr);
        LocalTime base = LocalTime.of(0, 0);
        Duration overT = Tservice.timediff(form.getCheckInTime(), form.getCheckOutTime(), form.getBreakTime());
        LocalTime overtime = base.plus(overT);
        Duration workT = Tservice.timediff(form.getCheckInTime(), form.getCheckOutTime(), form.getBreakTime(), overtime);
        LocalTime worktime = base.plus(workT);
        LocalDateTime currentDateTime = LocalDateTime.now();

        AttendanceEntity existing = getattendancerepository.findByEmpIdAndWorkDate(employeeId, form.getWorkDate());
        if (existing == null) {
            model.addAttribute("alertMessage", "その日の勤怠情報はありません。登録してください");
            return "alertBack";
        }

        int type = leavetypeservice.leaveTypeChecker(existing.getLeaveType(), form.getLeaveType());
        holidaymathService.incrementdecrement(type, employeeId);

        AttendanceEntity e = new AttendanceEntity();
        e.setEmpId(employeeId);
        e.setWorkDate(form.getWorkDate());
        e.setLeaveType(form.getLeaveType());
        e.setRemarks(form.getRemarks());
        e.setApproval(0);
        e.setUpdatedAt(currentDateTime);

        if ("年休".equals(form.getLeaveType())) {
            e.setCheckInTime(LocalTime.of(0, 0));
            e.setCheckOutTime(LocalTime.of(0, 0));
            e.setOvertimeHours(LocalTime.of(0, 0));
            e.setWorkTimeHours(LocalTime.of(7, 0));
            e.setBreakTime(LocalTime.of(0, 0));
        } else if ("振休".equals(form.getLeaveType()) || "振出".equals(form.getLeaveType())) {
            e.setCheckInTime(LocalTime.of(0, 0));
            e.setCheckOutTime(LocalTime.of(0, 0));
            e.setOvertimeHours(LocalTime.of(0, 0));
            e.setWorkTimeHours(LocalTime.of(0, 0));
            e.setBreakTime(LocalTime.of(0, 0));
        } else {
            e.setCheckInTime(form.getCheckInTime());
            e.setCheckOutTime(form.getCheckOutTime());
            e.setBreakTime(form.getBreakTime());
            e.setOvertimeHours(overtime);
            e.setWorkTimeHours(worktime);
        }

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
        return "Edit_complete";
    }

    @GetMapping("/Attendance_edit")
    public String showEditerForm(Model model, HttpSession session) {
        String employeeId = (String) session.getAttribute("employeeId");
        if (employeeId == null) {
            model.addAttribute("alertMessage", "ログイン情報が無効です。再度ログインしてください。");
            return "alertTop";
        }

        model.addAttribute("employeeId", employeeId);
        model.addAttribute("AttendancetForm", new AttendancetForm());
        System.out.println("取得した社員番号: " + employeeId);
        return "Attendance_edit";
    }
}
