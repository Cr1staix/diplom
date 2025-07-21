package ru.top.diplom.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.top.diplom.dto.computerSpecificationDTO.ComputerSpecificationCreateDTO;
import ru.top.diplom.dto.computerSpecificationDTO.ComputerSpecificationResponseDTO;
import ru.top.diplom.dto.computerSpecificationDTO.ComputerSpecificationUpdateDTO;
import ru.top.diplom.model.ComputerSpecification;

@Mapper(componentModel = "spring")
public interface ComputerSpecificationMapper {

    ComputerSpecification toComputerSpecification (ComputerSpecificationCreateDTO computerSpecificationCreateDTO);

    ComputerSpecificationResponseDTO toComputerSpecificationResponseDTO(ComputerSpecification computerSpecification);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromComputerSpecificationDTO(ComputerSpecificationUpdateDTO computerSpecificationUpdateDTO,
                                            @MappingTarget ComputerSpecification computerSpecification);
}
