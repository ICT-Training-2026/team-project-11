package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "holiday")
public class Holiday {
    
	@Id
    @Column(name = "EMP_ID")
    private int employeeId;

	@Column(name = "paid")
	private int paid;
	    
	@Column(name = "substitute")
	private int substitute;
    
    @ManyToOne
    private User user; // Userエンティティとの関係

    // ゲッターやセッター
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getSubstitute() {
        return substitute;
    }

    public void setSubstitute(int substitute) {
        this.substitute = substitute;
    }
}