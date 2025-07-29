package com.example.demo.repository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.entity.AttendanceEntity;

public class AdminApprovalRepositoryImplTest {
	private JdbcTemplate jdbcTemplate;
    private AdminApprovalRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        repository = new AdminApprovalRepositoryImpl(jdbcTemplate);
    }

    @Test
    void testFindUnapprovedAttendance() {
        System.out.println("=== testFindUnapprovedAttendance 開始 ===");

        AttendanceEntity entity = new AttendanceEntity();
        entity.setEmpId(1);  // Integer型に合わせる

        List<AttendanceEntity> mockList = Arrays.asList(entity);

        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<BeanPropertyRowMapper<AttendanceEntity>>any()))
            .thenReturn(mockList);

        List<AttendanceEntity> result = repository.findUnapprovedAttendance();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getEmpId()).isEqualTo(1);

        System.out.println("=== testFindUnapprovedAttendance 終了 ===");
    }


    @Test
    void testDeleteAttendance_success() {
        System.out.println("=== testDeleteAttendance_success 開始 ===");

        when(jdbcTemplate.update(anyString(), anyLong(), any(LocalDate.class))).thenReturn(1);

        repository.deleteAttendance(1L, LocalDate.of(2025, 7, 29));

        verify(jdbcTemplate, times(1)).update(anyString(), eq(1L), eq(LocalDate.of(2025, 7, 29)));

        System.out.println("=== testDeleteAttendance_success 終了 ===");
    }


    @Test
    void testDeleteAttendance_noRecord() {
        System.out.println("=== testDeleteAttendance_noRecord 開始 ===");

        when(jdbcTemplate.update(anyString(), any(), any())).thenReturn(0);

        IllegalArgumentException exception = catchThrowableOfType(() -> {
            repository.deleteAttendance(1L, LocalDate.of(2025, 7, 29));
        }, IllegalArgumentException.class);

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("該当するレコードが見つかりませんでした。");

        System.out.println("=== testDeleteAttendance_noRecord 終了 ===");
    }
    
    
}
