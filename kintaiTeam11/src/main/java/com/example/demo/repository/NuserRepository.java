package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

public interface NuserRepository extends JpaRepository<User, String> {
    // 追加で必要なメソッドがあればここに宣言
    User findByEmployeeId(String employeeId);
}