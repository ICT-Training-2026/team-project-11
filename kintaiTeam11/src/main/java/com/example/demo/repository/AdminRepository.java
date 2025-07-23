package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AtAd;

public interface AdminRepository extends JpaRepository<AtAd, String> {
    AtAd findByEmployeeId(String employeeId);
}