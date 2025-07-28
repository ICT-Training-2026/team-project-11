package com.example.demo.repository;

public interface AttendanceDeleteRepository {
    void deleteByEmployeeId(Integer employeeId); // 指定した社員IDでレコードを削除するメソッド
}