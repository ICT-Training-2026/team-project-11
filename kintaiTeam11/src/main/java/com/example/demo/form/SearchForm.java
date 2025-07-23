package com.example.demo.form;

import java.time.LocalDate;

import lombok.Data;
@Data
public class SearchForm {
	
	 private Integer empId;
	 private LocalDate workDate;
	 private String searchRange;

}
