package org.example.militarysystem.mapper;

import org.example.militarysystem.dto.UserDto;
import org.example.militarysystem.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);
}
