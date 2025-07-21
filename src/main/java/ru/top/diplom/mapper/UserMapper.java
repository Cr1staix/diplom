package ru.top.diplom.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.top.diplom.dto.userDTO.UserCreateDTO;
import ru.top.diplom.dto.userDTO.UserResponseDTO;
import ru.top.diplom.dto.userDTO.UserUpdateDTO;
import ru.top.diplom.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreateDTO userCreateDTO);

    UserResponseDTO toResponseUserDTO(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDTO(UserUpdateDTO userUpdateDTO, @MappingTarget User u);
}
