package com.example.msorder.service;

import com.example.msorder.model.User;
import com.example.msorder.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnAllUsers() {
        // given
        var user1 = User.builder().id(UUID.fromString("8a45689e-639e-11ee-8c99-0242ac120001")).build();
        var user2 = User.builder().id(UUID.fromString("8a45689e-639e-11ee-8c99-0242ac120002")).build();
        // when
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        var users = userService.findAll();
        // then
        verify(userRepository, times(1)).findAll();
        assertEquals(2, users.size());
    }

    @Test
    void shouldReturnUserWhenFindById() {
        // given
        var id = UUID.fromString("8a45689e-639e-11ee-8c99-0242ac120002");
        var user = User.builder().id(id).build();
        // when
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));
        var userFound = userService.findById(id);
        // then
        verify(userRepository, times(1)).findById(id);
        assertEquals(id, userFound.getId());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenUserNotFound() {
        // given
        var id = UUID.randomUUID();
        // when
        var exception = assertThrows(IllegalArgumentException.class, () -> userService.findById(id));
        var expectedMessage = "User not found";
        var actualMessage = exception.getMessage();
        // then
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void shouldSaveUser() {
        // given
        var user = User.builder().id(UUID.randomUUID()).build();
        // when
        when(userRepository.save(user)).thenReturn(user);
        var savedUser = userService.save(user);
        // then
        verify(userRepository, times(1)).save(user);
    }

}