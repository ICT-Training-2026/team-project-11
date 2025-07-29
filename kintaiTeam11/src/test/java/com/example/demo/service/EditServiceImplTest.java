package com.example.demo.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.entity.AttendanceEntity;
import com.example.demo.repository.EditRepository;

public class EditServiceImplTest {
	
	@Mock
    private EditRepository repository;

    private EditServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new EditServiceImpl(repository);
    }

    @Test
    void testRegist_callsUpdate() {
        // Arrange
        AttendanceEntity entity = new AttendanceEntity();
        entity.setEmpId(1);
        
        // Act
        service.regist(entity);

        // Assert
        verify(repository, times(1)).update(entity);
    }

}
