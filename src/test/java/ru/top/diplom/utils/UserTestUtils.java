package ru.top.diplom.utils;

import ru.top.diplom.dto.userDTO.SignUpRequest;
import ru.top.diplom.dto.userDTO.UserFilterDTO;
import ru.top.diplom.dto.userDTO.UserUpdateDTO;
import ru.top.diplom.enums.UserRole;
import ru.top.diplom.enums.UserStatus;
import ru.top.diplom.model.User;

import java.time.LocalDate;

public class UserTestUtils {
    public static User createTestUser() {
        return User.builder()
                .id(1L)
                .firstName("Василий")
                .lastName("Васильев")
                .phone("88005353535")
                .dateOfBirth(LocalDate.of(1990, 2, 1))
                .role(UserRole.USER)
                .status(UserStatus.BRONZE)
                .password("password")
                .build();
    }

    public static SignUpRequest createTestUserCreateDTO(){
        return SignUpRequest.builder()
                .phone("88005353535")
                .password("password")
                .dateOfBirth(LocalDate.of(1990, 2, 1))
                .build();
    }

    public static UserUpdateDTO createTestUserUpdateDTO(){
        return UserUpdateDTO.builder()
                .id(1L)
                .firstName("Василий")
                .lastName("Васильев")
                .phone("88005353535")
                .dateOfBirth(LocalDate.of(1990, 2, 1))
                .build();
    }

    public static UserFilterDTO createUserFilterDTO(){
        return UserFilterDTO.builder()
                .firstName("серг")
                .lastName("петр")
                .build();
    }
}
