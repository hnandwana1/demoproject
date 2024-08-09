package com.allen.machinecoding.demo.repository;

import com.allen.machinecoding.demo.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class UserRepositoryTest {

    @InjectMocks
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        UserEntity user = new UserEntity();
        user.setName("John Doe");

        UserEntity savedUser = userRepository.save(user);
        assertNotNull(savedUser);
        assertEquals("John Doe", savedUser.getName());
    }

    @Test
    public void testFindUserById() {
        UserEntity user = new UserEntity();
        user.setName("Jane Doe");

        userRepository.save(user);
        Optional<UserEntity> foundUser = userRepository.findById(user.getId());

        assertTrue(foundUser.isPresent());
        assertEquals("Jane Doe", foundUser.get().getName());
    }

    @Test
    public void testUpdateUser() {
        UserEntity user = new UserEntity();
        user.setName("John Doe");

        userRepository.save(user);
        user.setName("Johnny Doe");
        UserEntity updatedUser = userRepository.save(user);

        assertEquals("Johnny Doe", updatedUser.getName());
    }

    @Test
    public void testDeleteUser() {
        UserEntity user = new UserEntity();
        user.setName("John Doe");

        userRepository.save(user);
        userRepository.deleteById(user.getId());

        Optional<UserEntity> deletedUser = userRepository.findById(user.getId());
        assertFalse(deletedUser.isPresent());
    }

    @Test
    public void testFindAllUsers() {
        UserEntity user1 = new UserEntity();
        user1.setName("John Doe");

        UserEntity user2 = new UserEntity();
        user2.setName("Jane Doe");

        userRepository.save(user1);
        userRepository.save(user2);

        Iterable<UserEntity> users = userRepository.findAll();
        assertEquals(2, ((Collection<?>) users).size());
    }
}