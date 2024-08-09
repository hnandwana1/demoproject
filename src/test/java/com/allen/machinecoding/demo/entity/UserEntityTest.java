package com.allen.machinecoding.demo.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {
    @Test
    void testUserEntity() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("John Doe");

        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getName());
    }
}