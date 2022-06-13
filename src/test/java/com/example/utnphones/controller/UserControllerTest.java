package com.example.utnphones.controller;

import com.example.utnphones.AbstractTest;
import com.example.utnphones.dto.LoginRequestDto;
import com.example.utnphones.dto.UserRequestDto;
import com.example.utnphones.model.Role;
import com.example.utnphones.service.JwtService;
import com.example.utnphones.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = UserController.class)
class UserControllerTest extends AbstractTest {

    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private ModelMapper modelMapper;

    @Test
    void loginTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aJsonLoginRequest()))
                .andExpect(status().isOk());
    }

    @Test
    void registerTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aJsonUserRequest()))
                .andExpect(status().isCreated());
    }

    private LoginRequestDto aLoginRequest() {
        return LoginRequestDto.builder()
                .username("user")
                .password("1234")
                .build();
    }

    private UserRequestDto aUserRequest() {
        return UserRequestDto.builder()
                .username("user")
                .password("1234")
                .role(Role.EMPLOYEE)
                .build();
    }

    private String aJsonLoginRequest(){
        final Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return prettyGson.toJson(aLoginRequest());
    }

    private String aJsonUserRequest() {
        final Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return prettyGson.toJson(aUserRequest());
    }
}
