package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Holiday;

public interface HolidaymathRepository extends JpaRepository<Holiday, Integer> {
    Holiday findByEmployeeId(Integer employeeId);
}
