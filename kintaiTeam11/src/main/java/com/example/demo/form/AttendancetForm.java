package com.example.demo.form;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data

public class AttendancetForm {
	private Integer empId;
	private LocalDate workDate;
	private String leaveType;
	private LocalTime checkInTime;
	private LocalTime checkOutTime;
	private LocalTime breakTime;
	private LocalTime overtimeHours;
	private Integer consecutiveDays;
	private LocalTime workTimeHours;
	private String remarks;
	private Integer approval;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime updatedAt;
}