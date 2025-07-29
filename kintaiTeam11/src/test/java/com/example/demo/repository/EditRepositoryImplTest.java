package com.example.demo.repository;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.entity.AttendanceEntity;


public class EditRepositoryImplTest {
	
	@Mock
    private JdbcTemplate jdbcTemplate;

    private EditRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new EditRepositoryImpl(jdbcTemplate);
    }

    @Test
    void testUpdate() {
        AttendanceEntity entity = new AttendanceEntity();
        entity.setLeaveType("有給");
        entity.setCheckInTime(LocalTime.of(9, 0));
        entity.setCheckOutTime(LocalTime.of(18, 0));
        entity.setBreakTime(LocalTime.of(1, 0));
        entity.setOvertimeHours(LocalTime.of(0, 30));
        entity.setConsecutiveDays(5);
        entity.setWorkTimeHours(LocalTime.of(8, 0));
        entity.setRemarks("特になし");
        entity.setApproval(1);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setEmpId(123);
        entity.setWorkDate(LocalDate.of(2025, 7, 29));

        when(jdbcTemplate.update(anyString(), 
                eq(entity.getLeaveType()),
                eq(entity.getCheckInTime()),
                eq(entity.getCheckOutTime()),
                eq(entity.getBreakTime()),
                eq(entity.getOvertimeHours()),
                eq(entity.getConsecutiveDays()),
                eq(entity.getWorkTimeHours()),
                eq(entity.getRemarks()),
                eq(entity.getApproval()),
                eq(entity.getUpdatedAt()),
                eq(entity.getEmpId()),
                eq(entity.getWorkDate())
            )).thenReturn(1);

        repository.update(entity);

        verify(jdbcTemplate, times(1)).update(anyString(),
            eq(entity.getLeaveType()),
            eq(entity.getCheckInTime()),
            eq(entity.getCheckOutTime()),
            eq(entity.getBreakTime()),
            eq(entity.getOvertimeHours()),
            eq(entity.getConsecutiveDays()),
            eq(entity.getWorkTimeHours()),
            eq(entity.getRemarks()),
            eq(entity.getApproval()),
            eq(entity.getUpdatedAt()),
            eq(entity.getEmpId()),
            eq(entity.getWorkDate())
        );
    }
	
	

}
