package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.entity.AttendanceEntity;
import com.example.demo.repository.AttendanceQueryRepository;

public class AttendanceServiceImplTest {
	
	@Mock
    private AttendanceQueryRepository repository;

    private AttendanceServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new AttendanceServiceImpl(repository);
    }

    @Test
    void testGetPreviousAttendance_returnsEntity() {
        Integer empId = 1;
        LocalDate date = LocalDate.of(2025, 7, 29);

        AttendanceEntity entity = new AttendanceEntity();
        entity.setEmpId(empId);

        when(repository.findByEmpIdAndWorkDate(empId, date)).thenReturn(entity);

        AttendanceEntity result = service.getPreviousAttendance(empId, date);

        assertThat(result).isNotNull();
        assertThat(result.getEmpId()).isEqualTo(empId);

        verify(repository, times(1)).findByEmpIdAndWorkDate(empId, date);
    }

    @Test
    void testIsAlreadyRegistered_whenExists_returnsTrue() {
        Integer empId = 1;
        LocalDate date = LocalDate.of(2025, 7, 29);

        when(repository.findByEmpIdAndWorkDate(empId, date)).thenReturn(new AttendanceEntity());

        boolean result = service.isAlreadyRegistered(empId, date);

        assertThat(result).isTrue();
    }

    @Test
    void testIsAlreadyRegistered_whenNotExists_returnsFalse() {
        Integer empId = 2;
        LocalDate date = LocalDate.of(2025, 7, 29);

        when(repository.findByEmpIdAndWorkDate(empId, date)).thenReturn(null);

        boolean result = service.isAlreadyRegistered(empId, date);

        assertThat(result).isFalse();
    }

}
