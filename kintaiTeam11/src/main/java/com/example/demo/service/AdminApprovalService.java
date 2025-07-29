package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.AttendanceEntity;
import com.example.demo.repository.AdminApprovalRepositoryImpl;
@Service
public class AdminApprovalService {
	private final AdminApprovalRepositoryImpl repository;

    public AdminApprovalService(AdminApprovalRepositoryImpl repository) {
        this.repository = repository;
    }

    public List<AttendanceEntity> getUnapprovedAttendance() {
        return repository.findUnapprovedAttendance();
    }
    
    
    public void deleteAttendance(Long empId, LocalDate workDate) {
        repository.deleteAttendance(empId, workDate);
    }
    
    
    
    
    public void approveAttendance(Long empId, LocalDate workDate) {
        repository.approveAttendance(empId, workDate);
    }
}