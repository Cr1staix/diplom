package ru.top.diplom.mapper;

import org.mapstruct.Mapper;
import ru.top.diplom.dto.cityDTO.CityCreateDTO;
import ru.top.diplom.dto.cityDTO.CityResponseDTO;
import ru.top.diplom.model.City;

@Mapper(componentModel = "spring")
public interface CityMapper {

    City toCity (CityCreateDTO cityCreateDTO);

    CityResponseDTO toResponseDTO (City city);
}
