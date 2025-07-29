package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.example.demo.entity.Holiday;
import com.example.demo.entity.User;
import com.example.demo.repository.HolidayRepository;
import com.example.demo.repository.NuserRepository;

public class AddControllerTest {

    @InjectMocks
    private AddController addController;

    @Mock
    private NuserRepository nuserRepository;

    @Mock
    private HolidayRepository holidayRepository;

    @Mock
    private Model model;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(addController).build();
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        String employeeId = "123";
        String username = "Test User";
        String email = "test@example.com";
        String password = "password";
        String department = "1";
        int role = 1;

        when(nuserRepository.existsByEmployeeId(employeeId)).thenReturn(false);
        when(holidayRepository.save(any(Holiday.class))).thenReturn(new Holiday());

        mockMvc.perform(post("/Register")
                .param("employeeId", employeeId)
                .param("username", username)
                .param("email", email)
                .param("password", password)
                .param("department", department)
                .param("role", String.valueOf(role))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Admin"));

        verify(nuserRepository, times(1)).save(any(User.class));
        verify(holidayRepository, times(1)).save(any(Holiday.class));
    }

    @Test
    public void testRegisterUser_DuplicateEmployeeId() throws Exception {
        String employeeId = "123";
        String username = "Test User";
        String email = "test@example.com";
        String password = "password";
        String department = "1";
        int role = 1;

        when(nuserRepository.existsByEmployeeId(employeeId)).thenReturn(true);

        mockMvc.perform(post("/Register")
                .param("employeeId", employeeId)
                .param("username", username)
                .param("email", email)
                .param("password", password)
                .param("department", department)
                .param("role", String.valueOf(role))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_add"))
                .andExpect(model().attributeExists("duplicateId"));
    }

    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/Register"))
               .andExpect(status().isOk())
               .andExpect(view().name("admin_add"));
    }
}