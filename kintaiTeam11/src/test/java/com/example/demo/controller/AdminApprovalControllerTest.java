package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.AdminApprovalService;

public class AdminApprovalControllerTest {

	@InjectMocks
	private AdminApprovalController adminApprovalController;

	@Mock
	private AdminApprovalService service;

	@Mock
	private Model model;

	@Mock
	private RedirectAttributes redirectAttributes;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
	    MockitoAnnotations.openMocks(this);
	    mockMvc = MockMvcBuilders.standaloneSetup(adminApprovalController).build();
	}


	@Test
	public void testApproveAttendance_Success() throws Exception {
	    // Arrange
	    Long empId = 1L;
	    LocalDate workDate = LocalDate.now();
	    doNothing().when(service).approveAttendance(empId, workDate);

	    // Act
	    mockMvc.perform(post("/admin/attendance/approve")
	            .param("empId", String.valueOf(empId))
	            .param("workDate", workDate.toString())
	            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
	            .andExpect(status().is3xxRedirection())
	            .andExpect(redirectedUrl("/admin_approval"));

	    // Assert
	    verify(service, times(1)).approveAttendance(empId, workDate);
	    verify(redirectAttributes, times(1)).addFlashAttribute("message", "申請が承認されました。");
	}

	@Test
	public void testApproveAttendance_Failure() throws Exception {
	    // Arrange
	    Long empId = 1L;
	    LocalDate workDate = LocalDate.now();
	    doThrow(new RuntimeException("Error")).when(service).approveAttendance(empId, workDate);

	    // Act
	    mockMvc.perform(post("/admin/attendance/approve")
	            .param("empId", String.valueOf(empId))
	            .param("workDate", workDate.toString())
	            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
	            .andExpect(status().is3xxRedirection())
	            .andExpect(redirectedUrl("/admin_approval"));

	    // Assert
	    verify(service, times(1)).approveAttendance(empId, workDate);
	    verify(redirectAttributes, times(1)).addFlashAttribute("error", "承認処理中にエラーが発生しました。");
	}

	@Test
	public void testRejectAttendance_Success() throws Exception {
	    // Arrange
	    Long empId = 1L;
	    LocalDate workDate = LocalDate.now();
	    doNothing().when(service).deleteAttendance(empId, workDate);

	    // Act
	    mockMvc.perform(post("/admin/attendance/reject")
	            .param("empId", String.valueOf(empId))
	            .param("workDate", workDate.toString())
	            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
	            .andExpect(status().is3xxRedirection())
	            .andExpect(redirectedUrl("/admin_approval"));

	    // Assert
	    verify(service, times(1)).deleteAttendance(empId, workDate);
	    verify(redirectAttributes, times(1)).addFlashAttribute("message", "該当の申請が却下されました。");
	}

	@Test
	public void testRejectAttendance_Failure() throws Exception {
	    // Arrange
	    Long empId = 1L;
	    LocalDate workDate = LocalDate.now();
	    doThrow(new RuntimeException("Error")).when(service).deleteAttendance(empId, workDate);

	    // Act
	    mockMvc.perform(post("/admin/attendance/reject")
	            .param("empId", String.valueOf(empId))
	            .param("workDate", workDate.toString())
	            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
	            .andExpect(status().is3xxRedirection())
	            .andExpect(redirectedUrl("/admin_approval"));

	    // Assert
	    verify(service, times(1)).deleteAttendance(empId, workDate);
	    verify(redirectAttributes, times(1)).addFlashAttribute("error", "削除中にエラーが発生しました。");
	}
}