package com.example.demo.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.demo.entity.AttendanceEntity;
import com.example.demo.repository.AttendanceQueryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    
    private final AttendanceQueryRepository attendanceQueryRepository;

    @Override
    public AttendanceEntity getPreviousAttendance(Integer empId, LocalDate workDate) {
        return attendanceQueryRepository.findByEmpIdAndWorkDate(empId, workDate);
    }

}