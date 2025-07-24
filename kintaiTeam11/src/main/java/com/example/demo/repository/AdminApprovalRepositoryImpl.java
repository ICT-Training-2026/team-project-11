package com.example.demo.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AttendanceEntity;
@Repository
public class AdminApprovalRepositoryImpl {
	

	private final JdbcTemplate jdbcTemplate;

    public AdminApprovalRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AttendanceEntity> findUnapprovedAttendance() {
        String sql = "SELECT emp_id, work_date, leave_type, check_in_time, check_out_time, " +
                     "break_time, overtime_hours, consecutive_days, work_time_hours, remarks, approval, updated_at " +
                     "FROM attendance WHERE approval = 0";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AttendanceEntity.class));
    }
    
    
    
    
    public void deleteAttendance(Long empId, LocalDate workDate) {
    	String sql = "UPDATE attendance SET approval = 1 WHERE emp_id = ? AND work_date = ?";
        int rowsAffected = jdbcTemplate.update(sql, empId, workDate);
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("該当するレコードが見つかりませんでした。");
        }
    }
    
    
}