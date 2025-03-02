package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.configuration.SecurityConfig;
import com.example.demo.payload.EmailPasswordRequest;
import com.example.demo.service.JWTUtils;
import com.example.demo.service.UserService;
import com.example.demo.shared.RequestBodyAsString;

@ExtendWith(MockitoExtension.class)
@Import(SecurityConfig.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @InjectMocks
    AuthController authController;

    @MockBean
    private UserService userService;

    @MockBean
    private JWTUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @InjectMocks
    private RequestBodyAsString objectToString;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void ParametersAndRequestSuccessTest() throws Exception {
        EmailPasswordRequest content = new EmailPasswordRequest("admin@admin.com", "password");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToString.getString(content)))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());
        assertEquals("{\"message\":\"Your account is created\"}", result.getResponse().getContentAsString());
    }

    @Test
    public void EmailDontGiven() throws Exception {
        EmailPasswordRequest content = new EmailPasswordRequest("", "password");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToString.getString(content)))
                .andReturn();

        assertEquals(300, result.getResponse().getStatus());
        assertEquals("please give an email", result.getResponse().getContentAsString());

        content.setPassword("");

        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToString.getString(content)))
                .andReturn();

        assertEquals(300, result2.getResponse().getStatus());
        assertEquals("please give an email", result2.getResponse().getContentAsString());
    }

    @Test
    public void PasswordDontGiven() throws Exception {
        EmailPasswordRequest content = new EmailPasswordRequest("", "password");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToString.getString(content)))
                .andReturn();

        assertEquals(300, result.getResponse().getStatus());
        assertEquals("please give an email", result.getResponse().getContentAsString());
    }
}
