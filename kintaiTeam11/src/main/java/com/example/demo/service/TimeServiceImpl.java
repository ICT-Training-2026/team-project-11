package com.example.demo.service;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

@Service
public class TimeServiceImpl implements TimeService{

	@Override
	public Duration timediff(LocalTime time1, LocalTime time2) {
		
		return Duration.between(time1, time2);
	}

	@Override
	public Duration timediff(LocalTime time1, LocalTime time2, LocalTime time3) {
		LocalTime defo =LocalTime.of(7, 0);
		LocalTime base =LocalTime.of(0, 0);
	    Duration fdiff = Duration.between(time1, time2);
	    Duration sdiff = Duration.between(time3, defo);
	    Duration tdiff = fdiff.minus(sdiff);
	    
	    LocalTime overtime =base.plus(tdiff);
	    
	    if (overtime.isBefore(defo)){
	    	return tdiff;
	    }
	    return Duration.ZERO;
	    
	}

	@Override
	public Duration timediff(LocalTime time1, LocalTime time2, LocalTime time3, LocalTime time4) {
	    Duration fdiff = Duration.between(time1, time2);
	    Duration sdiff = Duration.between(time3, time4);
	    return fdiff.minus(sdiff);
	}

	
}
