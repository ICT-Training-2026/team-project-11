package com.example.demo.service;

public interface HolidayService {
    void incrementPaid(int employeeId); // paidカラムを+1
    void decrementPaid(int employeeId); // paidカラムを-1
    void incrementSubstitute(int employeeId); // substituteカラムを+1
    void decrementSubstitute(int employeeId); // substituteカラムを-1
}