package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.AttendanceEntity;
import com.example.demo.repository.EditRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditServiceImpl implements EditService {

	private final EditRepository repository;
	
	@Override
	public void regist(AttendanceEntity attendance) {
        repository.update(attendance);
    }
}