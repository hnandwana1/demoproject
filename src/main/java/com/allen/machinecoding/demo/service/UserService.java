package com.allen.machinecoding.demo.service;


import com.allen.machinecoding.demo.entity.UserEntity;
import com.allen.machinecoding.demo.model.UserDTO;
import com.allen.machinecoding.demo.repository.UserRepository;
import com.allen.machinecoding.demo.request.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO);
    }

    public UserDTO createUser(UserRequest userRequest) {
        UserEntity userEntity = convertToEntity(userRequest);
        UserEntity savedUser = userRepository.save(userEntity);
        return convertToDTO(savedUser);
    }

    public UserDTO updateUser(Long id, UserRequest userRequest) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(userRequest.getName());
                    existingUser.setAddress(userRequest.getAddress());
                    UserEntity updatedUser = userRepository.save(existingUser);
                    return convertToDTO(updatedUser);
                })
                .orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .address(userEntity.getAddress())
                .build();
    }

    private UserEntity convertToEntity(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userRequest.getName());
        userEntity.setAddress(userRequest.getAddress());
        return userEntity;
    }

}