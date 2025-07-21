package ru.top.diplom.dto.computerSpecificationDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ComputerSpecificationUpdateDTO {
    private Long id;
    private String monitor;
    private String keyboard;
    private String mouse;
    private String headphones;
    private String cpu;
    private String gpu;
    private String ram;
}
