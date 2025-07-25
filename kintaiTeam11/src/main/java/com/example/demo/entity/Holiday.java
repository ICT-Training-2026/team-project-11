package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "holiday")
public class Holiday {
	@Id
    private String employeeId;
    
	private int paid;
	
	private int substitute;
}