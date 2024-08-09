package com.allen.machinecoding.demo.service;

import com.allen.machinecoding.demo.entity.UserEntity;
import com.allen.machinecoding.demo.model.UserDTO;
import com.allen.machinecoding.demo.repository.UserRepository;
import com.allen.machinecoding.demo.request.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setAddress("123 Street");

        UserEntity userEntity = new UserEntity();
        userEntity.setName(userRequest.getName());
        userEntity.setAddress(userRequest.getAddress());

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDTO createdUser = userService.createUser(userRequest);
        assertEquals("John Doe", createdUser.getName());
        assertEquals("123 Street", createdUser.getAddress());
    }

    @Test
    public void testUpdateUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Johnny Doe");
        userRequest.setAddress("456 Avenue");

        UserEntity existingUser = new UserEntity();
        existingUser.setId(1L);
        existingUser.setName("John Doe");
        existingUser.setAddress("123 Street");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(UserEntity.class))).thenReturn(existingUser);

        UserDTO updatedUser = userService.updateUser(1L, userRequest);

        assertEquals("Johnny Doe", updatedUser.getName());
        assertEquals("456 Avenue", updatedUser.getAddress());
    }

    @Test
    public void testGetUserById() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("John Doe");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<UserDTO> foundUser = userService.getUserById(1L);
        assertEquals("John Doe", foundUser.get().getName());
    }

    @Test
    public void testGetAllUsers() {
        UserEntity user1 = new UserEntity();
        user1.setName("John Doe");

        UserEntity user2 = new UserEntity();
        user2.setName("Jane Doe");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserDTO> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void testDeleteUser() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}