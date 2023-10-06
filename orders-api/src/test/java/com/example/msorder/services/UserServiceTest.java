package com.example.msorder.services;

import com.example.msorder.models.User;
import com.example.msorder.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}