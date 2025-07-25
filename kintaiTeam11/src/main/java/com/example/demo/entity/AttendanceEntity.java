package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity

@Table(name = "attendance") 
public class AttendanceEntity {
	@Id
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
