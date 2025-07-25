package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Holiday;
public interface HolidayRepository extends JpaRepository<Holiday, String> {
    // 必要に応じて他のメソッドを追加
	Holiday findByEmployeeId(String employeeId);
}