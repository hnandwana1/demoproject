package com.user.management.service;


import com.user.management.entity.UserEntity;
import com.user.management.exception.UserNotFoundException;
import com.user.management.model.UserDTO;
import com.user.management.repository.UserRepository;
import com.user.management.request.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        log.debug("Fetching all users with pagination: {}", pageable);
        return userRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    @Cacheable(value = "userById", key = "#id")
    public UserDTO getUserById(UUID id) {
        log.debug("Fetching user with ID: {}", id);
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public UserDTO createUser(UserRequest userRequest) {
        log.debug("Creating new user: {}", userRequest.name());
        UserEntity userEntity = convertToEntity(userRequest);
        UserEntity savedUser = userRepository.save(userEntity);
        log.info("User created successfully with ID: {}", savedUser.getId());
        return convertToDTO(savedUser);
    }

    @Transactional
    @CacheEvict(value = "userById", key = "#id")
    public UserDTO updateUser(UUID id, UserRequest userRequest) {
        log.debug("Updating user with ID: {}", id);
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(userRequest.name());
                    existingUser.setAddress(userRequest.address());
                    UserEntity updatedUser = userRepository.save(existingUser);
                    log.info("User updated successfully with ID: {}", updatedUser.getId());
                    return convertToDTO(updatedUser);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    @CacheEvict(value = "userById", key = "#id")
    public void deleteUser(UUID id) {
        log.debug("Deleting user with ID: {}", id);
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }

    private UserDTO convertToDTO(UserEntity userEntity) {
        return new UserDTO(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getAddress()
        );
    }

    private UserEntity convertToEntity(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userRequest.name());
        userEntity.setAddress(userRequest.address());
        return userEntity;
    }
}