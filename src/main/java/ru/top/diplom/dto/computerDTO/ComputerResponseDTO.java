package ru.top.diplom.dto.computerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


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
    private ComputerSpecificationDTO computerSpecificationDTO;

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
    }
}
