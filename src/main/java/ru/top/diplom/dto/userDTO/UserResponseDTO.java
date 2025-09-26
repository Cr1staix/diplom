package ru.top.diplom.dto.userDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.top.diplom.enums.UserRole;
import ru.top.diplom.enums.UserStatus;
import ru.top.diplom.model.Balance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phone;
    private UserRole role;
    private UserStatus status;
    private BigDecimal balance;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime addedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;
}
