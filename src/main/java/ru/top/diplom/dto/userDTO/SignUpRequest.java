package ru.top.diplom.dto.userDTO;

import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.top.diplom.validation.ValidPassword;
import ru.top.diplom.validation.ValidPhoneNumber;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SignUpRequest {

    @ValidPhoneNumber
    private String phone;

    @ValidPassword
    private String password;

    @Past
    private LocalDate dateOfBirth;
}