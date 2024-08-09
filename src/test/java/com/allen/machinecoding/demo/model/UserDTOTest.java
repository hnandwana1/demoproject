package com.allen.machinecoding.demo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {
    @Test
    void testUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("John Doe");

        assertEquals(1L, userDTO.getId());
        assertEquals("John Doe", userDTO.getName());
    }
}