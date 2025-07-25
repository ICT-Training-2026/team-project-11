package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

public interface NuserRepository extends JpaRepository<User, String> {
    
    // 社員IDからユーザーを取得
    User findByEmployeeId(String employeeId);

    // 社員IDが既に存在するか確認（重複登録防止用）
    boolean existsByEmployeeId(String employeeId);
}