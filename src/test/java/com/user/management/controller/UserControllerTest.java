package com.user.management.controller;


import com.user.management.model.UserDTO;
import com.user.management.request.UserRequest;
import com.user.management.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final UUID userId1 = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");
    private final UUID userId2 = UUID.fromString("b2c3d4e5-f6a7-8901-bcde-f23456789012");

    @Test
    void testCreateUser() {
        var userRequest = new UserRequest("John Doe", "123 Street");
        var userDTO = new UserDTO(userId1, "John Doe", "123 Street");

        when(userService.createUser(any(UserRequest.class))).thenReturn(userDTO);

        var response = userController.createUser(userRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
        verify(userService, times(1)).createUser(userRequest);
    }

    @Test
    void testUpdateUser() {
        var userRequest = new UserRequest("Johnny Doe", "456 Avenue");
        var userDTO = new UserDTO(userId1, "Johnny Doe", "456 Avenue");

        when(userService.updateUser(eq(userId1), any(UserRequest.class))).thenReturn(userDTO);

        var response = userController.updateUser(userId1, userRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
        verify(userService, times(1)).updateUser(userId1, userRequest);
    }

    @Test
    void testGetUserById() {
        var user = new UserDTO(userId1, "John Doe", "123 Street");

        when(userService.getUserById(userId1)).thenReturn(user);

        var response = userController.getUserById(userId1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).getUserById(userId1);
    }

    @Test
    void testGetAllUsers() {
        var user1 = new UserDTO(userId1, "John Doe", "123 Street");
        var user2 = new UserDTO(userId2, "Jane Doe", "456 Avenue");

        var pageable = PageRequest.of(0, 10);
        var userPage = new PageImpl<>(Arrays.asList(user1, user2), pageable, 2);

        when(userService.getAllUsers(any(Pageable.class))).thenReturn(userPage);

        var response = userController.getAllUsers(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getContent().size());
        assertEquals(user1, response.getBody().getContent().get(0));
        assertEquals(user2, response.getBody().getContent().get(1));
        verify(userService, times(1)).getAllUsers(pageable);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userService).deleteUser(userId1);

        var response = userController.deleteUser(userId1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId1);
    }
}