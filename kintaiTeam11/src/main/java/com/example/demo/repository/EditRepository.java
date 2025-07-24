package com.example.demo.repository;

import org.springframework.stereotype.Service;

import com.example.demo.entity.AttendanceEntity;



@Service
public interface EditRepository { 
    
	  void update(AttendanceEntity attendance);
}
