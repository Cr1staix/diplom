package ru.top.diplom.dto.computerDTO;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.top.diplom.enums.ComputerStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ComputerFilterDTO {
    private String name;
    private ComputerStatus status;
    private Long specId;
    private String cpu;
    private Integer ram;
    private Integer gpuMemory;
    private String gpu;
    private UUID id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAfter;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAfter;
}
