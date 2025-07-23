package com.example.demo.repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AttendanceEntity;

@Repository
public class SearchRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SearchRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AttendanceEntity> findByEmpIdAndWorkDate(Integer empId, LocalDate workDate) {
        String sql = """
            SELECT EMP_ID, WORK_DATE, leave_type, check_in_time, check_out_time,
                   break_time, overtime_hours, consecutive_days,
                   work_time_hours, remarks, approval, updated_at
            FROM Attendance
            WHERE EMP_ID = ? AND WORK_DATE = ?
        """;
        return jdbcTemplate.query(
            sql,
            new BeanPropertyRowMapper<>(AttendanceEntity.class),
            empId, Date.valueOf(workDate)
        );
    }
    
    public List<AttendanceEntity> findByEmpIdAndWorkDateBetween(Integer empId, LocalDate start, LocalDate end) {
        String sql = """
            SELECT EMP_ID, WORK_DATE, leave_type, check_in_time, check_out_time,
                   break_time, overtime_hours, consecutive_days,
                   work_time_hours, remarks, approval, updated_at
            FROM Attendance
            WHERE EMP_ID = ? AND WORK_DATE BETWEEN ? AND ?
        """;
        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(AttendanceEntity.class),
                empId,
                Date.valueOf(start),
                Date.valueOf(end)
            );
    }
}