package ru.top.diplom.dto.computerDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ComputerResponseDTO {
    private UUID id;
    private String name;
    private Boolean isActive;
    private ComputerSpecificationDTO computerSpecificationDTO;
    private BigDecimal pricePerHour;

    @Getter
    @AllArgsConstructor
    @Setter
    @NoArgsConstructor
    @Builder
    public static class ComputerSpecificationDTO{
        private String monitor;
        private String keyboard;
        private String mouse;
        private String headphones;
        private String cpu;
        private String gpu;
        private String ram;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime addedAt;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime updatedAt;
    }
}
