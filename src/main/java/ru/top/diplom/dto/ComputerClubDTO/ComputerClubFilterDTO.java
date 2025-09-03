package ru.top.diplom.dto.ComputerClubDTO;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ComputerClubFilterDTO {
    private String name;
    private String address;
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime addedAfter;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAfter;
    private Long userId;
    private Long computerId;
    private Double minBalance;
    private Double maxBalance;
}
