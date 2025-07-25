package com.example.demo.repository;

import java.time.LocalDate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import com.example.demo.entity.AttendanceEntity;

public abstract class AttendanceQueryRepositoryImpl implements AttendanceQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public AttendanceEntity findCustomByEmpIdAndWorkDate(Integer empId, LocalDate workDate) {
        String jpql = "SELECT a FROM AttendanceEntity a WHERE a.empId = :empId AND a.workDate = :workDate";
        TypedQuery<AttendanceEntity> query = entityManager.createQuery(jpql, AttendanceEntity.class);
        query.setParameter("empId", empId);
        query.setParameter("workDate", workDate);
        return query.getResultStream().findFirst().orElse(null);
    }

}