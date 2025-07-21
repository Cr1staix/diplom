package ru.top.diplom.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubCreateDTO;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubResponseDTO;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubUpdateDTO;
import ru.top.diplom.model.ComputerClub;

@Mapper(componentModel = "spring")
public interface ComputerClubMapper {

    ComputerClub toComputerClub (ComputerClubCreateDTO computerClubCreateDTO);

    ComputerClubResponseDTO toComputerClubResponseDTO (ComputerClub computerClub);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateComputerClubFromDTO (ComputerClubUpdateDTO computerClubUpdateDTO, @MappingTarget ComputerClub computerClub);
}
