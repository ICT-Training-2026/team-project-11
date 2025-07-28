package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Holiday;
import com.example.demo.repository.HolidaymathRepository;

@Service
public class HolidaymathServiceImpl implements HolidaymathService {

    @Autowired
    private HolidaymathRepository holidaymathRepository;

    
    public void incrementPaid(Integer employeeId) {
        Holiday holiday = holidaymathRepository.findByEmployeeId(employeeId);
        if (holiday != null) {
            holiday.setPaid(holiday.getPaid() + 1); // paidカラムを+1する
            holidaymathRepository.save(holiday);
        } else {
            throw new RuntimeException("Holiday record not found for employeeId: " + employeeId);
        }
    }

    
    public void decrementPaid(Integer employeeId) {
        Holiday holiday = holidaymathRepository.findByEmployeeId(employeeId);
        if (holiday != null) {
            holiday.setPaid(holiday.getPaid() - 1); // paidカラムを-1する
            holidaymathRepository.save(holiday);
        } else {
            throw new RuntimeException("Holiday record not found for employeeId: " + employeeId);
        }
    }

    
    public void incrementSubstitute(Integer employeeId) {
        Holiday holiday = holidaymathRepository.findByEmployeeId(employeeId);
        if (holiday != null) {
            holiday.setSubstitute(holiday.getSubstitute() + 1); // substituteカラムを+1する
            holidaymathRepository.save(holiday);
        } else {
            throw new RuntimeException("Holiday record not found for employeeId: " + employeeId);
        }
    }

    public void decrementSubstitute(Integer employeeId) {
        Holiday holiday = holidaymathRepository.findByEmployeeId(employeeId);
        if (holiday != null) {
            holiday.setSubstitute(holiday.getSubstitute() - 1); // substituteカラムを-1する
            holidaymathRepository.save(holiday);
        } else {
            throw new RuntimeException("Holiday record not found for employeeId: " + employeeId);
        }
    }

	@Override
	public void incrementdecrement(int type, Integer employeeId) {
		switch (type) {
        case 1:
            incrementPaid(employeeId);
            break;
        case 2:
        	incrementPaid(employeeId);
        	incrementSubstitute(employeeId);
            break;
        case 3:
        	incrementPaid(employeeId);
        	decrementSubstitute(employeeId);
            break;
        case 4:
        	decrementPaid(employeeId);
            break;
        case 5:
        	decrementSubstitute(employeeId);
            break;
        case 6:
        	incrementSubstitute(employeeId);
            break;
        case 7:
        	decrementSubstitute(employeeId);
        	decrementPaid(employeeId);
            break;
        case 8:
        	decrementSubstitute(employeeId);
            break;
        case 9:
        	decrementSubstitute(employeeId);
        	decrementSubstitute(employeeId);
            break;
        case 10:
        	decrementPaid(employeeId);
        	incrementSubstitute(employeeId);
            break;
        case 11:
        	incrementSubstitute(employeeId);
            break;
        case 12:
        	incrementSubstitute(employeeId);
        	incrementSubstitute(employeeId);
            break;
        case 0:
        	
            break;
    }
		
	}
}