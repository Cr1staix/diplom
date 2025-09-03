package ru.top.diplom.dto.userDTO;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.top.diplom.enums.UserRole;
import ru.top.diplom.enums.UserStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UserFilterDTO {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phone;
    private UserRole role;
    private UserStatus status;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime addedAfter;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAfter;

}