package com.example.msorder.dtos.mappers;

import com.example.msorder.dtos.UserDto;
import com.example.msorder.models.User;
import com.example.msorder.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UserRepository repository;

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(user.getId(), user.getName());
    }

    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        var user = new User();
        if (dto.id() != null) {
            user.setId(dto.id());
        }
        user.setName(dto.name());
        return user;
    }

}
