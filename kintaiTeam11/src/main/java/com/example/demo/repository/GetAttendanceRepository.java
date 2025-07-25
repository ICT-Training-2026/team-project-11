package com.example.demo.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AttendanceEntity;

public interface GetAttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
    AttendanceEntity findByEmpIdAndWorkDate(Integer empId, LocalDate workDate);
}