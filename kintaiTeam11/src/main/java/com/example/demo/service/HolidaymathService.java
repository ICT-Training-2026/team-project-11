package com.example.demo.service;

public interface HolidaymathService {
    void incrementPaid(String employeeId); // paidカラムを+1
    void decrementPaid(String employeeId); // paidカラムを-1
    void incrementSubstitute(String employeeId); // substituteカラムを+1
    void decrementSubstitute(String employeeId); // substituteカラムを-1
}