package ru.top.diplom.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.top.diplom.dto.computerDTO.ComputerCreateDTO;
import ru.top.diplom.dto.computerDTO.ComputerResponseDTO;
import ru.top.diplom.dto.computerDTO.ComputerUpdateDTO;
import ru.top.diplom.model.Computer;

@Mapper(componentModel = "spring")
public interface ComputerMapper {

    Computer toComputer(ComputerCreateDTO computerCreateDTO);

    ComputerResponseDTO toComputerResponseDTO (Computer computer);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateComputerForDTO(ComputerUpdateDTO computerUpdateDTO, @MappingTarget Computer computer);
}
