package com.example.demo.service;

import java.time.LocalDate;

import com.example.demo.entity.AttendanceEntity;

public interface AttendanceService {
    AttendanceEntity getPreviousAttendance(Integer empId, LocalDate workDate);
    boolean isAlreadyRegistered(Integer empId, LocalDate workDate); // ← 追加
}
