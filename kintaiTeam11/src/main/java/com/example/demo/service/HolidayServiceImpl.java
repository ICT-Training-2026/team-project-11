package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Holiday;
import com.example.demo.repository.HolidayRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {

	private final HolidayRepository repository;


	@Override
	public void holiadd(Holiday holiday) {
		repository.add(holiday);
		
	}
}