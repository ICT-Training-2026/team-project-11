package com.example.demo.repository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.entity.AttendanceEntity;

public class SearchRepositoryTest {
	
	@Mock
    private JdbcTemplate jdbcTemplate;

    private SearchRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new SearchRepository(jdbcTemplate);
    }

    @Test
    void testFindByEmpIdAndWorkDate() {
        Integer empId = 1;
        LocalDate workDate = LocalDate.of(2025, 7, 29);

        AttendanceEntity entity = new AttendanceEntity();
        entity.setEmpId(empId);
        entity.setWorkDate(workDate);
        List<AttendanceEntity> mockResult = Arrays.asList(entity);

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), eq(empId), eq(Date.valueOf(workDate))))
            .thenReturn(mockResult);

        List<AttendanceEntity> result = repository.findByEmpIdAndWorkDate(empId, workDate);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getEmpId()).isEqualTo(empId);
    }

    @Test
    void testFindByEmpIdAndWorkDateBetween() {
        Integer empId = 2;
        LocalDate start = LocalDate.of(2025, 7, 1);
        LocalDate end = LocalDate.of(2025, 7, 31);

        AttendanceEntity entity = new AttendanceEntity();
        entity.setEmpId(empId);
        entity.setWorkDate(LocalDate.of(2025, 7, 15));
        List<AttendanceEntity> mockResult = Arrays.asList(entity);

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), eq(empId), eq(Date.valueOf(start)), eq(Date.valueOf(end))))
            .thenReturn(mockResult);

        List<AttendanceEntity> result = repository.findByEmpIdAndWorkDateBetween(empId, start, end);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getEmpId()).isEqualTo(empId);
    }

}
