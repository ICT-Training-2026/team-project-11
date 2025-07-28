package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Holiday;

public interface DeleteholidayRepository extends JpaRepository<Holiday, String> {
    void deleteByEmployeeId(String employeeId);
}