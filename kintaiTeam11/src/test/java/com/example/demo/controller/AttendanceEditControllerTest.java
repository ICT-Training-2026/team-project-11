package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.entity.AttendanceEntity;
import com.example.demo.form.AttendancetForm;
import com.example.demo.repository.GetAttendanceRepository;
import com.example.demo.service.AttendanceService;
import com.example.demo.service.EditService;
import com.example.demo.service.HolidaymathService;
import com.example.demo.service.LeaveTypeService;
import com.example.demo.service.TimeService;

@WebMvcTest(AttendanceEditController.class)
public class AttendanceEditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EditService editService;

    @MockBean
    private TimeService timeService;

    @MockBean
    private AttendanceService attendanceService;

    @MockBean
    private LeaveTypeService leaveTypeService;

    @MockBean
    private GetAttendanceRepository getAttendanceRepository;

    @MockBean
    private HolidaymathService holidaymathService;

    private MockHttpSession session;

    @BeforeEach
    public void setUp() {
        session = new MockHttpSession(); // MockHttpSessionのインスタンスを作成
        session.setAttribute("employeeId", "12345"); // セッションにemployeeIdを設定
    }

    @Test
    public void testAttendanceEditComplate_ValidInput() throws Exception {
        // Mocking the AttendanceEntity to return a valid attendance record
        AttendanceEntity attendance = new AttendanceEntity();
        attendance.setLeaveType("出勤"); // 例として出勤を設定
        when(getAttendanceRepository.findByEmpIdAndWorkDate(anyInt(), any(LocalDate.class))).thenReturn(attendance);

        AttendancetForm form = new AttendancetForm();
        form.setWorkDate(LocalDate.now());
        form.setLeaveType("出勤");
        form.setCheckInTime(LocalTime.of(9, 0));
        form.setCheckOutTime(LocalTime.of(17, 0));
        form.setBreakTime(LocalTime.of(1, 0));
        form.setRemarks("テスト");

        mockMvc.perform(post("/Edit_complete")
                .session(session)
                .param("workDate", form.getWorkDate().toString())
                .param("leaveType", form.getLeaveType())
                .param("checkInTime", form.getCheckInTime().toString())
                .param("checkOutTime", form.getCheckOutTime().toString())
                .param("breakTime", form.getBreakTime().toString())
                .param("remarks", form.getRemarks()))
                .andExpect(status().isOk())
                .andExpect(view().name("Edit_complete")); // 期待されるビュー名
    }

    @Test
    public void testAttendanceEditComplate_InvalidInput() throws Exception {
        AttendancetForm form = new AttendancetForm();
        form.setWorkDate(null); // 無効な日付

        mockMvc.perform(post("/Edit_complete")
                .session(session)
                .param("workDate", "")
                .param("leaveType", "")
                .param("checkInTime", "")
                .param("checkOutTime", "")
                .param("breakTime", "")
                .param("remarks", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("Atmenu")); // エラーが発生した場合のビュー
    }

    @Test
    public void testAttendanceEditComplate_SessionInvalid() throws Exception {
        AttendancetForm form = new AttendancetForm();
        form.setWorkDate(LocalDate.now());
        form.setLeaveType("出勤");

        // セッションにemployeeIdを設定しない
        mockMvc.perform(post("/Edit_complete")
                .session(new MockHttpSession()) // 空のセッションを使用
                .param("workDate", form.getWorkDate().toString())
                .param("leaveType", form.getLeaveType()))
                .andExpect(status().isOk())
                .andExpect(view().name("Atmenu")); // セッションが無効な場合のビュー
    }
}