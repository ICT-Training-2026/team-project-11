package com.example.demo.repository;

import java.time.LocalDate;

public interface AttendanceDeleteRepository {
    void deleteByEmployeeId(Integer employeeId); // 指定した社員IDでレコードを削除するメソッド
    void deleteByEmployeeIdAndWorkdate(Integer employeeId,LocalDate workDate);
}