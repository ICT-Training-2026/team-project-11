package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AttendanceEntity;

@Repository
public class ExportRepository{
    private final JdbcTemplate jdbcTemplate;
    
    
    public ExportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AttendanceEntity> findByDateRange(LocalDate start, LocalDate end) {
        String sql = "SELECT * FROM Attendance WHERE work_date BETWEEN ? AND ? ORDER BY emp_id, work_date";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AttendanceEntity.class), start, end);
    }

    public List<AttendanceEntity> findByEmpAndDateRange(Long empId, LocalDate start, LocalDate end) {
        String sql = "SELECT * FROM Attendance WHERE emp_id = ? AND work_date BETWEEN ? AND ? ORDER BY work_date";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AttendanceEntity.class), empId, start, end);
    }
    
    public List<AttendanceEntity> findByMonth(int year, int month) {
        String sql = "SELECT * FROM Attendance WHERE DATE_FORMAT(work_date, '%Y-%m') = ?";
        String monthStr = String.format("%04d-%02d", year, month);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AttendanceEntity.class), monthStr);
    }
}