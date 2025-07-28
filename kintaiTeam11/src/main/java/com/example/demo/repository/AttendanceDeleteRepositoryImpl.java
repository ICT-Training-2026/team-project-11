package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class AttendanceDeleteRepositoryImpl implements AttendanceDeleteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void deleteByEmployeeId(Integer employeeId) {
        String sql = "DELETE FROM attendance WHERE EMP_ID = ?";
        int rowsAffected = jdbcTemplate.update(sql, employeeId);
        
        if (rowsAffected == 0) {
            System.out.println("指定した社員IDに該当するレコードは存在しません。");
        } else {
            System.out.println("レコードを削除しました: " + employeeId);
        }
    }
}