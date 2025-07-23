package com.example.demo.service;
import java.time.Duration;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

@Service
public interface TimeService {
	Duration timediff(LocalTime time1,LocalTime time2);
	
	Duration timediff(LocalTime time1,LocalTime time2,LocalTime time3);
	
	Duration timediff(LocalTime time1,LocalTime time2,LocalTime time3,LocalTime time4);
	
}
