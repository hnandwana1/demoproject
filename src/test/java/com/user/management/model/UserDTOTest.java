package com.user.management.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {
    
    private final UUID testUserId = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");
    
    @Test
    void testUserDTO() {
        var userDTO = new UserDTO(testUserId, "John Doe", "123 Street");

        assertEquals(testUserId, userDTO.id());
        assertEquals("John Doe", userDTO.name());
        assertEquals("123 Street", userDTO.address());
    }
    
    @Test
    void testUserDTOEquality() {
        var userDTO1 = new UserDTO(testUserId, "John Doe", "123 Street");
        var userDTO2 = new UserDTO(testUserId, "John Doe", "123 Street");
        
        assertEquals(userDTO1, userDTO2);
        assertEquals(userDTO1.hashCode(), userDTO2.hashCode());
    }
}