package com.example.demo.repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AttendanceEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Service
public class EditRepositoryImpl implements EditRepository {
	private final JdbcTemplate jdbcTemplate;
	
	@Override
    public void update(AttendanceEntity a) {
        String sql = "UPDATE attendance SET " +
                     "leave_type = ?, check_in_time = ?, check_out_time = ?, break_time = ?, overtime_hours = ?, consecutive_days = ?, work_time_hours = ?, remarks = ?, approval = ?, updated_at = ? " +
                     "WHERE EMP_ID = ? AND WORK_DATE = ?"; // 更新の条件を指定
        
        jdbcTemplate.update(sql,
            a.getLeaveType(),
            a.getCheckInTime(),
            a.getCheckOutTime(),
            a.getBreakTime(),
            a.getOvertimeHours(),
            a.getConsecutiveDays(),
            a.getWorkTimeHours(),
            a.getRemarks(),
            a.getApproval(),
            a.getUpdatedAt(),
            a.getEmpId(), // WHERE句で使用する
            a.getWorkDate() // WHERE句で使用する
        );
	}
}