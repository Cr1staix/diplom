package ru.top.diplom.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.top.diplom.dto.computerDTO.ComputerCreateDTO;
import ru.top.diplom.dto.computerDTO.ComputerResponseDTO;
import ru.top.diplom.dto.computerDTO.ComputerUpdateDTO;
import ru.top.diplom.model.ClubPricing;
import ru.top.diplom.model.Computer;
import ru.top.diplom.model.ComputerSpecification;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ComputerMapper {

    Computer toComputer(ComputerCreateDTO computerCreateDTO);

    @Mapping(target = "computerSpecificationDTO", source = "computerSpecification")
    ComputerResponseDTO toComputerResponseDTO (Computer computer);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateComputerForDTO(ComputerUpdateDTO computerUpdateDTO, @MappingTarget Computer computer);

    default ComputerResponseDTO.ComputerSpecificationDTO toComputerSpecificationDTO(ComputerSpecification computerSpecification) {

        return  ComputerResponseDTO.ComputerSpecificationDTO.builder()
                .monitor(computerSpecification.getMonitor())
                .keyboard(computerSpecification.getKeyboard())
                .mouse(computerSpecification.getMouse())
                .headphones(computerSpecification.getHeadphones())
                .cpu(computerSpecification.getCpu())
                .gpu(computerSpecification.getGpu())
                .ram(computerSpecification.getRam())
                .build();

    }

}
