package ru.top.diplom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.diplom.dto.computerDTO.ComputerCreateDTO;
import ru.top.diplom.dto.computerDTO.ComputerFilterDTO;
import ru.top.diplom.dto.computerDTO.ComputerResponseDTO;
import ru.top.diplom.dto.computerDTO.ComputerUpdateDTO;
import ru.top.diplom.exception.computer.ComputerNotFoundException;
import ru.top.diplom.exception.computer_specification.ComputerSpecificationNotFoundException;
import ru.top.diplom.mapper.ComputerMapper;
import ru.top.diplom.model.Computer;
import ru.top.diplom.model.ComputerSpecification;
import ru.top.diplom.repository.ComputerRepository;
import ru.top.diplom.repository.ComputerSpecificationRepository;
import ru.top.diplom.specification.ComputerSpecificationCriteriaApi;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComputerService {

    private final ComputerRepository computerRepository;
    private final ComputerMapper computerMapper;
    private final ComputerSpecificationRepository computerSpecificationRepository;


    public ComputerResponseDTO create(ComputerCreateDTO computerCreateDTO){

       ComputerSpecification computerSpecification = computerSpecificationRepository.findById(computerCreateDTO.getSpecId())
               .orElseThrow(() -> new ComputerSpecificationNotFoundException(computerCreateDTO.getSpecId()));

        Computer computer = computerMapper.toComputer(computerCreateDTO);

        computer.setComputerSpecification(computerSpecification);

        return computerMapper.toComputerResponseDTO(computerRepository.save(computer));
    }

    public Page<ComputerResponseDTO> findAll(ComputerFilterDTO filter, Pageable pageable){

        Specification<Computer> spec = ComputerSpecificationCriteriaApi.createSpecification(filter);

       return computerRepository.findAll(spec, pageable).map(computerMapper::toComputerResponseDTO);
    }

    public ComputerResponseDTO findById(UUID id){

        Computer computer = computerRepository.findById(id)
                .orElseThrow(() -> new ComputerNotFoundException(id));

        return computerMapper.toComputerResponseDTO(computer);
    }

    @Transactional
    public ComputerResponseDTO update (ComputerUpdateDTO computerUpdateDTO){

        Computer computer = computerRepository.findById(computerUpdateDTO.getId())
                .orElseThrow(() -> new ComputerNotFoundException(computerUpdateDTO.getId()));

        if(computerUpdateDTO.getSpecId() != null){
            ComputerSpecification computerSpecification = computerSpecificationRepository.findById(computerUpdateDTO.getSpecId())
                    .orElseThrow(() -> new ComputerSpecificationNotFoundException(computerUpdateDTO.getSpecId()));

            computer.setComputerSpecification(computerSpecification);
        }

        computerMapper.updateComputerForDTO(computerUpdateDTO, computer);

        return computerMapper.toComputerResponseDTO(computerRepository.save(computer));
    }

    public void delete(UUID id){

        Computer computer = computerRepository.findById(id)
                .orElseThrow(() -> new ComputerNotFoundException(id));

        computerRepository.delete(computer);
    }

}
