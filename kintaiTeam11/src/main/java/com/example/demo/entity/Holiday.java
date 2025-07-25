package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "holiday")
public class Holiday {
   
	@Id
    private int employeeId; // 外部キーかつ主キーとして使用

    @OneToOne
    @MapsId // 同じIDを使用するためにMapsIdを使用
    @JoinColumn(name = "EMP_ID") // このカラムがEmployeeエンティティの主キーを参照
    private User user;

	@Column(name = "paid")
	private int paid;
	    
	@Column(name = "substitute")
	private int substitute;
   
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