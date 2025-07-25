package com.example.demo.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Holiday;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HolidayRepositoryImpl implements HolidayRepository {


	private final JdbcTemplate jdbcTemplate;
	@Override
	 public void add(Holiday h) {
		String sql=
				"INSERT INTO holiday"+
		        "(EMP_ID,paid,substitute)"+
						"VALUES (?,?,?)";
		

				 jdbcTemplate.update(sql,
				            h.getEmployeeId(),
				            h.getPaid(),
				            h.getSubstitute()
				         
				        );
				    }
				}