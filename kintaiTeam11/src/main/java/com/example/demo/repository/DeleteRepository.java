package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

public interface DeleteRepository extends JpaRepository<User, String> {
    void deleteByEmployeeId(String employeeId);
}