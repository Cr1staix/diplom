package ru.top.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.diplom.dto.computerSpecificationDTO.ComputerSpecFilterDTO;
import ru.top.diplom.dto.computerSpecificationDTO.ComputerSpecificationCreateDTO;
import ru.top.diplom.dto.computerSpecificationDTO.ComputerSpecificationResponseDTO;
import ru.top.diplom.dto.computerSpecificationDTO.ComputerSpecificationUpdateDTO;
import ru.top.diplom.exception.computer_specification.ComputerSpecificationNotFoundException;
import ru.top.diplom.mapper.ComputerSpecificationMapper;
import ru.top.diplom.model.ComputerSpecification;
import ru.top.diplom.repository.ComputerSpecificationRepository;
import ru.top.diplom.specification.ComputerSpecSpecificationCriteriaApi;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComputerSpecificationService {

    private final ComputerSpecificationRepository computerSpecificationRepository;
    private final ComputerSpecificationMapper computerSpecificationMapper;

    public ComputerSpecificationResponseDTO create (ComputerSpecificationCreateDTO computerSpecificationCreateDTO){

        ComputerSpecification computerSpecification = computerSpecificationMapper.toComputerSpecification(computerSpecificationCreateDTO);

        return computerSpecificationMapper.toComputerSpecificationResponseDTO(computerSpecificationRepository.save(computerSpecification));
    }

    public Page<ComputerSpecificationResponseDTO> findAll(ComputerSpecFilterDTO filter, Pageable pageable){

        Specification<ComputerSpecification> spec = ComputerSpecSpecificationCriteriaApi.createSpecification(filter);

        return computerSpecificationRepository.findAll(spec, pageable).map(computerSpecificationMapper::toComputerSpecificationResponseDTO);
    }

    public ComputerSpecificationResponseDTO findById(Long id){

        ComputerSpecification computerSpecification = computerSpecificationRepository.findById(id)
                .orElseThrow(() -> new ComputerSpecificationNotFoundException(id));

        return computerSpecificationMapper.toComputerSpecificationResponseDTO(computerSpecification);
    }

    @Transactional
    public ComputerSpecificationResponseDTO update(ComputerSpecificationUpdateDTO computerSpecificationUpdateDTO){

        ComputerSpecification computerSpecification = computerSpecificationRepository.findById(computerSpecificationUpdateDTO.getId())
                .orElseThrow(() -> new ComputerSpecificationNotFoundException(computerSpecificationUpdateDTO.getId()));

        computerSpecificationMapper.updateFromComputerSpecificationDTO(computerSpecificationUpdateDTO, computerSpecification);

        return computerSpecificationMapper.toComputerSpecificationResponseDTO(computerSpecificationRepository.save(computerSpecification));
    }

    public void delete (Long id){

        ComputerSpecification computerSpecification = computerSpecificationRepository.findById(id)
                .orElseThrow(() -> new ComputerSpecificationNotFoundException(id));

        computerSpecificationRepository.delete(computerSpecification);
    }
}
