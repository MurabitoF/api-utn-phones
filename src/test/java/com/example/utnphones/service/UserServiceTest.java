package com.example.utnphones.service;

import com.example.utnphones.model.Bill;
import com.example.utnphones.model.Role;
import com.example.utnphones.model.User;
import com.example.utnphones.repository.UserRepository;
import com.example.utnphones.service.impl.UserServiceImpl;
import com.example.utnphones.utils.MockModels;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

import static com.example.utnphones.utils.MockModels.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void getAllUsersTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Pageable pageable = PageRequest.of(0, 10);
        final Page<User> aUserPage = aUserPage();

        Mockito.when(userRepository.findAll(pageable)).thenReturn(aUserPage);

        Page<User> response = userService.getAllUsers(pageable);

        assertNotNull(response, "Should be not null");
        assertTrue(response.hasContent(), "Should have content");
        assertEquals(aUserPage, response);
    }

    @Test
    void loadUserByUsernameTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final String aUsername = "user";
        final User aUser = aUser();
        aUser.setUserId(1L);
        aUser.setPassword("1234");
        aUser.setRole(Role.EMPLOYEE);

        Mockito.when(userRepository.findByUsernameAndDeleteAtNull(aUsername)).thenReturn(Optional.of(aUser));

        final UserDetails response = userService.loadUserByUsername(aUsername);

        assertNotNull(response, "Should be not null");
        assertEquals(aUser, response);
    }

    @Test
    void loadUserByUsernameFailedTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final String aUsername = "user";

        Mockito.when(userRepository.findByUsernameAndDeleteAtNull(aUsername)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> { userService.loadUserByUsername(aUsername); });
    }

    @Test
    void saveNewUserTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final User aUser = aUser();
        aUser.setPassword("1234");
        aUser.setRole(Role.EMPLOYEE);
        final User aUserResponse = aUser;
        aUserResponse.setUserId(1L);

        Mockito.when(userRepository.save(aUser)).thenReturn(aUserResponse);

        final UserDetails response = userService.saveNewUser(aUser);

        assertNotNull(response, "Should be not null");
        assertEquals(aUser, response);
    }
}
