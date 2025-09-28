package ru.top.diplom.utils;

import ru.top.diplom.dto.computerDTO.ComputerCreateDTO;
import ru.top.diplom.dto.computerDTO.ComputerFilterDTO;
import ru.top.diplom.dto.computerDTO.ComputerUpdateDTO;
import ru.top.diplom.model.Computer;
import ru.top.diplom.model.ComputerSpecification;
import java.math.BigDecimal;
import java.util.UUID;

public class ComputerTestUtils {
    public static Computer createTestComputer() {
        Object ComputerStatus;
        return Computer.builder()
                .id(UUID.randomUUID())
                .name("Gaming PC 1")
                .isActive(false)
                .computerStatus(ru.top.diplom.enums.ComputerStatus.DEFAULT)
                .pricePerHour(new BigDecimal("100.00"))
                .computerSpecification(createTestComputerSpecification())
                .build();
    }

    public static ComputerCreateDTO createTestComputerCreateDTO() {
        return ComputerCreateDTO.builder()
                .name("Gaming PC 1")
                .specId(1L)
                .build();
    }

    public static ComputerUpdateDTO createTestComputerUpdateDTO() {
        return ComputerUpdateDTO.builder()
                .id(UUID.randomUUID())
                .name("Gaming PC Updated")
                .specId(1L)
                .build();
    }

    public static ComputerFilterDTO createTestComputerFilterDTO() {
        return ComputerFilterDTO.builder()
                .name("Gaming")
                .build();
    }

    public static ComputerSpecification createTestComputerSpecification() {
        return ComputerSpecification.builder()
                .id(1L)
                .monitor("27寸 IPS")
                .keyboard("Mechanical")
                .mouse("Gaming Mouse")
                .headphones("Gaming Headset")
                .cpu("Intel i7")
                .gpu("RTX 4070")
                .ram("32GB DDR5")
                .build();
    }
}
