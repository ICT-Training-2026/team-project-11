package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.entity.AttendanceEntity;
import com.example.demo.repository.AdminApprovalRepositoryImpl;

public class AdminApprovalServiceTest {
	
	@Mock
    private AdminApprovalRepositoryImpl repository;

    private AdminApprovalService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new AdminApprovalService(repository);
    }

    @Test
    void testGetUnapprovedAttendance() {
        AttendanceEntity mockEntity = new AttendanceEntity();
        mockEntity.setEmpId(1);

        when(repository.findUnapprovedAttendance())
            .thenReturn(Arrays.asList(mockEntity));

        List<AttendanceEntity> result = service.getUnapprovedAttendance();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmpId()).isEqualTo(1);

        verify(repository, times(1)).findUnapprovedAttendance();
    }

    @Test
    void testDeleteAttendance() {
        Long empId = 1L;
        LocalDate date = LocalDate.of(2025, 7, 29);

        service.deleteAttendance(empId, date);

        verify(repository, times(1)).deleteAttendance(empId, date);
    }

    @Test
    void testApproveAttendance() {
        Long empId = 2L;
        LocalDate date = LocalDate.of(2025, 7, 30);

        service.approveAttendance(empId, date);

        verify(repository, times(1)).deleteAttendance(empId, date);
    }

}
