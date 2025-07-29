package com.example.demo.repository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

public class ExportRepositoryTest {
	
	 @Mock
	    private JdbcTemplate jdbcTemplate;

	    private ExportRepository repository;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	        repository = new ExportRepository(jdbcTemplate);
	    }

	    @Test
	    void testFindByDateRange() {
	        LocalDate start = LocalDate.of(2025, 7, 1);
	        LocalDate end = LocalDate.of(2025, 7, 31);

	        AttendanceEntity entity = new AttendanceEntity();
	        entity.setEmpId(1);
	        entity.setWorkDate(LocalDate.of(2025, 7, 10));

	        List<AttendanceEntity> mockList = Arrays.asList(entity);

	        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), eq(start), eq(end)))
	            .thenReturn(mockList);

	        List<AttendanceEntity> result = repository.findByDateRange(start, end);

	        assertThat(result).isNotNull();
	        assertThat(result.size()).isEqualTo(1);
	        assertThat(result.get(0).getEmpId()).isEqualTo(1);

	        verify(jdbcTemplate, times(1))
	            .query(anyString(), any(BeanPropertyRowMapper.class), eq(start), eq(end));
	    }

	    @Test
	    void testFindByEmpAndDateRange() {
	        Long empId = 2L;
	        LocalDate start = LocalDate.of(2025, 7, 1);
	        LocalDate end = LocalDate.of(2025, 7, 31);

	        AttendanceEntity entity = new AttendanceEntity();
	        entity.setEmpId(empId.intValue());
	        entity.setWorkDate(LocalDate.of(2025, 7, 15));

	        List<AttendanceEntity> mockList = Arrays.asList(entity);

	        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), eq(empId), eq(start), eq(end)))
	            .thenReturn(mockList);

	        List<AttendanceEntity> result = repository.findByEmpAndDateRange(empId, start, end);

	        assertThat(result).isNotNull();
	        assertThat(result.size()).isEqualTo(1);
	        assertThat(result.get(0).getEmpId()).isEqualTo(empId.intValue());

	        verify(jdbcTemplate, times(1))
	            .query(anyString(), any(BeanPropertyRowMapper.class), eq(empId), eq(start), eq(end));
	    }

	    @Test
	    void testFindByMonth() {
	        int year = 2025;
	        int month = 7;
	        String monthStr = "2025-07";

	        AttendanceEntity entity = new AttendanceEntity();
	        entity.setEmpId(3);
	        entity.setWorkDate(LocalDate.of(year, month, 20));

	        List<AttendanceEntity> mockList = Arrays.asList(entity);

	        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), eq(monthStr)))
	            .thenReturn(mockList);

	        List<AttendanceEntity> result = repository.findByMonth(year, month);

	        assertThat(result).isNotNull();
	        assertThat(result.size()).isEqualTo(1);
	        assertThat(result.get(0).getEmpId()).isEqualTo(3);

	        verify(jdbcTemplate, times(1))
	            .query(anyString(), any(BeanPropertyRowMapper.class), eq(monthStr));
	    }

}
