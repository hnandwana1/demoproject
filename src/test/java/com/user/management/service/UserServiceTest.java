package com.user.management.service;

import com.user.management.entity.UserEntity;
import com.user.management.model.UserDTO;
import com.user.management.repository.UserRepository;
import com.user.management.request.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final UUID userId1 = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");
    private final UUID userId2 = UUID.fromString("b2c3d4e5-f6a7-8901-bcde-f23456789012");

    @Test
     void testCreateUser() {
        UserRequest userRequest = new UserRequest("John Doe", "123 Street");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId1);
        userEntity.setName("John Doe");
        userEntity.setAddress("123 Street");

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDTO createdUser = userService.createUser(userRequest);
        assertEquals("John Doe", createdUser.name());
        assertEquals("123 Street", createdUser.address());
    }

    @Test
     void testUpdateUser() {
        UserRequest userRequest = new UserRequest("Johnny Doe", "456 Avenue");

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId1);
        existingUser.setName("John Doe");
        existingUser.setAddress("123 Street");

        when(userRepository.findById(userId1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(UserEntity.class))).thenReturn(existingUser);

        UserDTO updatedUser = userService.updateUser(userId1, userRequest);

        assertEquals("Johnny Doe", updatedUser.name());
        assertEquals("456 Avenue", updatedUser.address());
    }

    @Test
     void testGetUserById() {
        UserEntity user = new UserEntity();
        user.setId(userId1);
        user.setName("John Doe");
        user.setAddress("123 Street");

        when(userRepository.findById(userId1)).thenReturn(Optional.of(user));

        UserDTO foundUser = userService.getUserById(userId1);
        assertEquals("John Doe", foundUser.name());
    }

    @Test
     void testGetAllUsers() {
        UserEntity user1 = new UserEntity();
        user1.setId(userId1);
        user1.setName("John Doe");
        user1.setAddress("123 Street");

        UserEntity user2 = new UserEntity();
        user2.setId(userId2);
        user2.setName("Jane Doe");
        user2.setAddress("456 Avenue");

        Pageable pageable = PageRequest.of(0, 10);
        Page<UserEntity> userPage = new PageImpl<>(Arrays.asList(user1, user2), pageable, 2);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        Page<UserDTO> users = userService.getAllUsers(pageable);
        assertEquals(2, users.getContent().size());
    }

    @Test
     void testDeleteUser() {
        when(userRepository.existsById(userId1)).thenReturn(true);
        doNothing().when(userRepository).deleteById(userId1);

        userService.deleteUser(userId1);
        verify(userRepository, times(1)).deleteById(userId1);
    }
}