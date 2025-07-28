package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService{
	

	@Override
	public int leaveTypeChecker(String before,String after) {
		if ("年休".equals(before)){
        	if("出勤".equals(after)){
        		return 1;
        	}
        	else if("振出".equals(after)) {
        		return 2;
        	}
        	else if("振休".equals(after)){
        		return 3;
        	};
        }
        if ("出勤".equals(before)) {
        	if("年休".equals(after)) {
        		return 4;
        	}
        	else if("振休".equals(after)) {
        		return 5;
        	}
        	else if("振出".equals(after)) {
        		return 6;
        	};
        }
        if ("振出".equals(before)) {
        	if("年休".equals(after)) {
        		return 7;
        	}
        	else if("出勤".equals(after)) {
        		return 8;
        	}
        	else if("振休".equals(after)) {
        		return 9;
        	};
        }
        if ("振休".equals(before)) {
        	if("年休".equals(after)) {
        		return 10;
        	}
        	else if("出勤".equals(after)) {
        		return 11;
        	}
        	else if("振出".equals(after)) {
        		return 12;
        	};
        }
        return 0;
		
	}

}
