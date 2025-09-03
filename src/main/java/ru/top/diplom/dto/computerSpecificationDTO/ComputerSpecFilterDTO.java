package ru.top.diplom.dto.computerSpecificationDTO;

import lombok.Data;

@Data
public class ComputerSpecFilterDTO {
    private String monitor;
    private String keyboard;
    private String mouse;
    private String headphones;
    private String cpu;
    private String gpu;
    private String ram;
    private Long id;
    private Integer minComputersCount;
    private Integer maxComputersCount;
}
