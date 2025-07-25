package com.example.demo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AttendanceEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AttendanceRepository2Impl implements AttendanceRepository {


	private final JdbcTemplate jdbcTemplate;
	@Override
	 public void add(AttendanceEntity a) {
		String sql=
				"INSERT INTO attendance"+
		        "(EMP_ID,WORK_DATE,leave_type, check_in_time, check_out_time,break_time, overtime_hours, consecutive_days, work_time_hours, remarks, approval, updated_at)"+
						"VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		

				 jdbcTemplate.update(sql,
				            a.getEmpId(),
				            a.getWorkDate(),
				            a.getLeaveType(),
				            a.getCheckInTime(),
				            a.getCheckOutTime(),
				            a.getBreakTime(),
				            a.getOvertimeHours(),
				            a.getConsecutiveDays(),
				            a.getWorkTimeHours(),
				            a.getRemarks(),
				            a.getApproval(),
				            a.getUpdatedAt()
				        );
				    }
				}