package com.example.demo.repository;

import org.springframework.stereotype.Service;

import com.example.demo.entity.AttendanceEntity;



@Service
public interface AttendanceRepository { 
    
	  void add(AttendanceEntity attendance);
}
