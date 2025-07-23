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
		
	    LocalTime fdiffL = base.plus(fdiff);
	    if(fdiffL.isAfter(time3)) {
	    	LocalTime save = fdiffL;
	    	fdiffL = time3;
	    	time3 = save;
	    }
	    Duration sdiff = Duration.between(fdiffL, time3);
	    LocalTime sdiffL = base.plus(sdiff);
	    if(sdiffL.isBefore(defo)) {
	    	return Duration.ZERO;
	    }
	    if(sdiffL.isAfter(defo)) {
	    	LocalTime save = sdiffL;
	    	sdiffL = defo;
	    	defo = save;
	    }
	    
	    Duration tdiff = Duration.between(sdiffL,defo);
	    
	    
	    return tdiff;
	    
	    
	}

	@Override
	public Duration timediff(LocalTime time1, LocalTime time2, LocalTime time3, LocalTime time4) {
	
		LocalTime base =LocalTime.of(0, 0);
		
		Duration fdiff = Duration.between(time1, time2);
		
	    LocalTime fdiffL = base.plus(fdiff);
	    if(fdiffL.isAfter(time3)) {
	    	LocalTime save = fdiffL;
	    	fdiffL = time3;
	    	time3 = save;
	    }
	    Duration sdiff = Duration.between(fdiffL, time3);
	    LocalTime sdiffL = base.plus(sdiff);
	    if(sdiffL.isAfter(time4)) {
	    	LocalTime save = sdiffL;
	    	sdiffL = time4;
	    	time4 = save;
	    }
	    Duration tdiff = Duration.between(sdiffL,time4);
	   
	    return tdiff;
	}

	
}