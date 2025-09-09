package ru.top.diplom.dto.ComputerClubDTO;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
public class ComputerClubFilterDTO {
    private String name;
    private Long cityId;
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
