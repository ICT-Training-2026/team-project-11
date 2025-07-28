package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "holiday")
public class Holiday {


    @Id
    @Column(name = "EMP_ID")
    private Integer employeeId;

    @Column(name = "paid")
    private int paid;

    @Column(name = "substitute")
    private int substitute;

    // --- Getter & Setter ---

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
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
    @ManyToOne
    @JoinColumn(name = "EMP_ID", insertable = false, updatable = false)
    private User user;

}

