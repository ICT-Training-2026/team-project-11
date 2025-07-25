package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Holiday;
import com.example.demo.repository.HolidaymathRepository;

@Service
public class HolidaymathServiceImpl implements HolidaymathService {

    @Autowired
    private HolidaymathRepository holidaymathRepository;

    @Override
    public void incrementPaid(String employeeId) {
        Holiday holiday = holidaymathRepository.findByEmployeeId(employeeId);
        if (holiday != null) {
            holiday.setPaid(holiday.getPaid() + 1); // paidカラムを+1する
            holidaymathRepository.save(holiday);
        } else {
            throw new RuntimeException("Holiday record not found for employeeId: " + employeeId);
        }
    }

    @Override
    public void decrementPaid(String employeeId) {
        Holiday holiday = holidaymathRepository.findByEmployeeId(employeeId);
        if (holiday != null) {
            holiday.setPaid(holiday.getPaid() - 1); // paidカラムを-1する
            holidaymathRepository.save(holiday);
        } else {
            throw new RuntimeException("Holiday record not found for employeeId: " + employeeId);
        }
    }

    @Override
    public void incrementSubstitute(String employeeId) {
        Holiday holiday = holidaymathRepository.findByEmployeeId(employeeId);
        if (holiday != null) {
            holiday.setSubstitute(holiday.getSubstitute() + 1); // substituteカラムを+1する
            holidaymathRepository.save(holiday);
        } else {
            throw new RuntimeException("Holiday record not found for employeeId: " + employeeId);
        }
    }

    @Override
    public void decrementSubstitute(String employeeId) {
        Holiday holiday = holidaymathRepository.findByEmployeeId(employeeId);
        if (holiday != null) {
            holiday.setSubstitute(holiday.getSubstitute() - 1); // substituteカラムを-1する
            holidaymathRepository.save(holiday);
        } else {
            throw new RuntimeException("Holiday record not found for employeeId: " + employeeId);
        }
    }
}