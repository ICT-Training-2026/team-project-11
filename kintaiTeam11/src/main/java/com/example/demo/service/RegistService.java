package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.AttendanceEntity;
@Service
public interface RegistService {
	void regist(AttendanceEntity attendance);
	}