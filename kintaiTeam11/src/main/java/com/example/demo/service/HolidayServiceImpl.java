package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Holiday;
import com.example.demo.repository.HolidayRepository;

@Service
public class HolidayServiceImpl implements HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    @Override
    public void incrementPaid(int employeeId) {
        Holiday holiday = holidayRepository.findByEmployeeId(employeeId);
        if (holiday != null) {
            holiday.setPaid(holiday.getPaid() + 1); // paidカラムを+1する
            holidayRepository.save(holiday);
        } else {
            throw new RuntimeException("Holiday record not found for employeeId: " + employeeId);
        }
    }

    @Override
    public void decrementPaid(int employeeId) {
        Holiday holiday = holidayRepository.findByEmployeeId(employeeId);
        if (holiday != null) {
            holiday.setPaid(holiday.getPaid() - 1); // paidカラムを-1する
            holidayRepository.save(holiday);
        } else {
            throw new RuntimeException("Holiday record not found for employeeId: " + employeeId);
        }
    }

    @Override
    public void incrementSubstitute(int employeeId) {
        Holiday holiday = holidayRepository.findByEmployeeId(employeeId);
        if (holiday != null) {
            holiday.setSubstitute(holiday.getSubstitute() + 1); // substituteカラムを+1する
            holidayRepository.save(holiday);
        } else {
            throw new RuntimeException("Holiday record not found for employeeId: " + employeeId);
        }
    }

    @Override
    public void decrementSubstitute(int employeeId) {
        Holiday holiday = holidayRepository.findByEmployeeId(employeeId);
        if (holiday != null) {
            holiday.setSubstitute(holiday.getSubstitute() - 1); // substituteカラムを-1する
            holidayRepository.save(holiday);
        } else {
            throw new RuntimeException("Holiday record not found for employeeId: " + employeeId);
        }
    }
}