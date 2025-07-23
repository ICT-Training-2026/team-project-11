package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.At1;

public interface UserRepository extends JpaRepository<At1, String> {
    At1 findByEmployeeIdAndPassword(String employeeId, String password);
}