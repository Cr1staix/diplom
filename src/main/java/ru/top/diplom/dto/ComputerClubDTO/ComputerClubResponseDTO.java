package ru.top.diplom.dto.ComputerClubDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.top.diplom.model.Computer;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ComputerClubResponseDTO {
    private Long id;
    private String name;
    private String address;
    private List<Computer> computers;
}
