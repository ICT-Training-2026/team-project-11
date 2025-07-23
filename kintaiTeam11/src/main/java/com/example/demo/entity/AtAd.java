package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Employees") // テーブル名を指定
public class AtAd{
    @Id
    @Column(name = "EMP_ID") 
    private String employeeId; // カラム名が employee_id である場合、@Column(name = "employee_id") を追加

    @Column(name = "role") 
    private int role;
    
    @Column(name = "DEP_ID") 
    private int DEP_ID;

    // ゲッターとセッター
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
    public int getDEP_ID() {
        return DEP_ID;
    }

    public void setDEP_ID(int DEP_ID) {
        this.DEP_ID = DEP_ID;
    }
}