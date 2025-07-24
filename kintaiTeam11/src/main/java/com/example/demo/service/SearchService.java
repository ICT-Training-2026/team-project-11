package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AttendanceEntity;
import com.example.demo.repository.SearchRepository;

@Service
public class SearchService {
	private final SearchRepository repo;

    @Autowired
    public SearchService(SearchRepository repo) {
        this.repo = repo;
    }

    public List<AttendanceEntity> findByEmpIdAndWorkDateBetween(
            Integer empId, LocalDate start, LocalDate end) {
        return repo.findByEmpIdAndWorkDateBetween(empId, start, end);
    }
}