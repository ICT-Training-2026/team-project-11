package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.entity.AtAd;
import com.example.demo.repository.AdminRepository;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // ここを変更
    private AdminRepository adminRepository;

    private MockHttpSession session;

    @BeforeEach
    public void setUp() {
        session = new MockHttpSession(); // MockHttpSessionのインスタンスを作成
    }

    @Test
    public void testCheckRole_AdminAccess() throws Exception {
        session.setAttribute("employeeId", "12345"); // セッションにemployeeIdを設定

        AtAd user = new AtAd();
        user.setRole(1); // 管理者権限
        when(adminRepository.findByEmployeeId("12345")).thenReturn(user);

        mockMvc.perform(get("/checkRole").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Admin"));
    }

    @Test
    public void testCheckRole_NoAdminAccess() throws Exception {
        session.setAttribute("employeeId", "12345"); // セッションにemployeeIdを設定

        AtAd user = new AtAd();
        user.setRole(0); // 権限なし
        user.setDEP_ID(1); // 部署ID
        when(adminRepository.findByEmployeeId("12345")).thenReturn(user);

        mockMvc.perform(get("/checkRole").session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("alertMessage", "管理者権限がありません。"))
                .andExpect(view().name("alertBack"));
    }

    @Test
    public void testCheckRole_SessionInvalid() throws Exception {
        // セッションにemployeeIdが存在しない
        mockMvc.perform(get("/checkRole").session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("alertMessage", "セッションが無効です。再度ログインしてください。"))
                .andExpect(view().name("alertBack"));
    }
}