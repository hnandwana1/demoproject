package com.user.management.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {
    
    private final UUID testUserId = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");
    
    @Test
    void testUserEntity() {
        var user = new UserEntity();
        user.setId(testUserId);
        user.setName("John Doe");
        user.setAddress("123 Street");

        assertEquals(testUserId, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("123 Street", user.getAddress());
    }
}