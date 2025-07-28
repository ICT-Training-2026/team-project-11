package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface LeaveTypeService {
	
	int leaveTypeChecker(String before,String after);
	
}