package com.allen.machinecoding.demo.controller;

import com.allen.machinecoding.demo.model.UserDTO;
import com.allen.machinecoding.demo.request.UserRequest;
import com.allen.machinecoding.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setAddress("123 Street");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("John Doe");
        userDTO.setAddress("123 Street");

        when(userService.createUser(any(UserRequest.class))).thenReturn(userDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\", \"address\":\"123 Street\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"name\":\"John Doe\",\"address\":\"123 Street\"}"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Johnny Doe");
        userRequest.setAddress("456 Avenue");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("Johnny Doe");
        userDTO.setAddress("456 Avenue");

        when(userService.updateUser(eq(1L), any(UserRequest.class))).thenReturn(userDTO);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Johnny Doe\", \"address\":\"456 Avenue\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Johnny Doe\",\"address\":\"456 Avenue\"}"));
    }


    @Test
    public void testGetUserById() throws Exception {
        UserDTO user = new UserDTO();
        user.setId(1L);
        user.setName("John Doe");

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"John Doe\"}"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        UserDTO user1 = new UserDTO();
        user1.setId(1L);
        user1.setName("John Doe");

        UserDTO user2 = new UserDTO();
        user2.setId(2L);
        user2.setName("Jane Doe");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"John Doe\"}, {\"id\":2,\"name\":\"Jane Doe\"}]"));
    }


    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }
}
