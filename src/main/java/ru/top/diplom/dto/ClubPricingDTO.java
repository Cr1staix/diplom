package ru.top.diplom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.top.diplom.enums.ComputerStatus;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubPricingDTO {
    private Long clubId;
    private ComputerStatus computerStatus;
    private BigDecimal pricePerHour;
}
