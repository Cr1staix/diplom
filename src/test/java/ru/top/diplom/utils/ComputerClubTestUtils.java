package ru.top.diplom.utils;

import ru.top.diplom.dto.ComputerClubDTO.ComputerClubCreateDTO;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubFilterDTO;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubUpdateDTO;
import ru.top.diplom.model.City;
import ru.top.diplom.model.ComputerClub;

public class ComputerClubTestUtils {
    public static ComputerClub createTestComputerClub() {
        return ComputerClub.builder()
                .id(1L)
                .name("GameZone")
                .address("ул. Пушкина, д. 10")
                .city(createTestCity())
                .build();
    }

    public static ComputerClubCreateDTO createTestComputerClubCreateDTO() {
        return ComputerClubCreateDTO.builder()
                .name("GameZone")
                .address("ул. Пушкина, д. 10")
                .city_id(1L)
                .build();
    }

    public static ComputerClubUpdateDTO createTestComputerClubUpdateDTO() {
        return ComputerClubUpdateDTO.builder()
                .id(1L)
                .name("GameZone Updated")
                .address("ул. Пушкина, д. 15")
                .build();
    }

    public static ComputerClubFilterDTO createTestComputerClubFilterDTO() {
        return ComputerClubFilterDTO.builder()
                .name("Game")
                .cityId(1L)
                .build();
    }

    public static City createTestCity() {
        return City.builder()
                .id(1L)
                .name("Москва")
                .build();
    }
}
