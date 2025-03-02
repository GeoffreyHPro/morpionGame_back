package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.model.User;
import com.example.demo.repository.userRepository.UserRepository;
import com.example.demo.repository.userRepository.UserRepositoryImpl;
import com.example.demo.service.JWTUtils;
import com.example.demo.service.UserService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    private UserService userService;

    @MockBean
    UserRepositoryImpl userRepositoryImpl;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private JWTUtils jwtUtils;

    @InjectMocks
    UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin@admin.com")
    public void GetUserInformations() throws Exception {
        User user = new User("admin@admin.com", "password");

        Mockito.when(userRepo.findByEmail("admin@admin.com")).thenReturn(user);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/user"))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());

        assertEquals("{\"email\":\"admin@admin.com\",\"name\":\"bonjour\"}", result.getResponse().getContentAsString());
    }

    @Test
    public void GetNoInformations() throws Exception {

        Mockito.when(userRepo.findByEmail("admin@admin.com")).thenReturn(null);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/user"))
                .andReturn();
        assertEquals(401, result.getResponse().getStatus());
    }
}
