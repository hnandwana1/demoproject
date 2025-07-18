package com.user.management.repository;

import com.user.management.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@Sql(scripts = "classpath:test-schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
 class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
     void testSaveUser() {
        var user = new UserEntity();
        user.setName("John Doe");
        user.setAddress("123 Street");

        var savedUser = userRepository.save(user);
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("John Doe", savedUser.getName());
        assertEquals("123 Street", savedUser.getAddress());
    }

    @Test
     void testFindUserById() {
        var user = new UserEntity();
        user.setName("Jane Doe");
        user.setAddress("456 Avenue");

        userRepository.save(user);
        var foundUser = userRepository.findById(user.getId());

        assertTrue(foundUser.isPresent());
        assertEquals("Jane Doe", foundUser.get().getName());
        assertEquals("456 Avenue", foundUser.get().getAddress());
    }

    @Test
     void testUpdateUser() {
        var user = new UserEntity();
        user.setName("John Doe");
        user.setAddress("123 Street");

        userRepository.save(user);
        user.setName("Johnny Doe");
        user.setAddress("456 Avenue");
        var updatedUser = userRepository.save(user);

        assertEquals("Johnny Doe", updatedUser.getName());
        assertEquals("456 Avenue", updatedUser.getAddress());
    }

    @Test
     void testDeleteUser() {
        var user = new UserEntity();
        user.setName("John Doe");
        user.setAddress("123 Street");

        userRepository.save(user);
        UUID userId = user.getId();
        userRepository.deleteById(userId);

        var deletedUser = userRepository.findById(userId);
        assertFalse(deletedUser.isPresent());
    }

    @Test
     void testFindAllUsers() {
        var user1 = new UserEntity();
        user1.setName("John Doe");
        user1.setAddress("123 Street");

        var user2 = new UserEntity();
        user2.setName("Jane Doe");
        user2.setAddress("456 Avenue");

        userRepository.save(user1);
        userRepository.save(user2);

        var users = userRepository.findAll();
        assertEquals(2, users.size());
    }
}