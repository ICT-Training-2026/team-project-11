package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.AttendanceEntity;
import com.example.demo.repository.AttendanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistServiceImpl implements RegistService {

	private final AttendanceRepository repository;
	
	@Override
	public void regist(AttendanceEntity attendance) {
        repository.add(attendance);
    }
}