package ru.top.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubCreateDTO;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubFilterDTO;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubResponseDTO;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubUpdateDTO;
import ru.top.diplom.exception.city.CityNotFoundException;
import ru.top.diplom.exception.computer_club.ComputerClubAllreadyExistsException;
import ru.top.diplom.exception.computer_club.ComputerClubNotFoundException;
import ru.top.diplom.mapper.ComputerClubMapper;
import ru.top.diplom.model.City;
import ru.top.diplom.model.ComputerClub;
import ru.top.diplom.repository.CityRepository;
import ru.top.diplom.repository.ComputerClubRepository;
import ru.top.diplom.specification.ComputerClubSpecificationCriteriaApi;


@Service
@RequiredArgsConstructor
public class ComputerClubService {

    private final ComputerClubRepository computerClubRepository;
    private final ComputerClubMapper computerClubMapper;
    private final CityRepository cityRepository;

    public ComputerClubResponseDTO create (ComputerClubCreateDTO computerClubCreateDTO){

        if(computerClubRepository.existsByNameAndAddress(computerClubCreateDTO.getName(), computerClubCreateDTO.getAddress())){

            throw new ComputerClubAllreadyExistsException(computerClubCreateDTO.getName(), computerClubCreateDTO.getAddress());
        }

        ComputerClub computerClub = computerClubMapper.toComputerClub(computerClubCreateDTO);

        City city = cityRepository.findById(computerClubCreateDTO.getCity_id())
                        .orElseThrow(() -> new CityNotFoundException(computerClubCreateDTO.getCity_id()));

        computerClub.setCity(city);

        computerClubRepository.save(computerClub);

        return computerClubMapper.toComputerClubResponseDTO(computerClub);
    }

    public Page<ComputerClubResponseDTO> findAll(ComputerClubFilterDTO filter, Pageable pageable){

        Specification<ComputerClub> spec = ComputerClubSpecificationCriteriaApi.createSpecification(filter);

       return computerClubRepository.findAll(spec, pageable).map(computerClubMapper::toComputerClubResponseDTO);
    }

    public ComputerClubResponseDTO findById(Long id){

        ComputerClub computerClub = computerClubRepository.findById(id)
                .orElseThrow(() -> new ComputerClubNotFoundException(id));

        return computerClubMapper.toComputerClubResponseDTO(computerClub);
    }

    @Transactional
    public ComputerClubResponseDTO update (ComputerClubUpdateDTO computerClubUpdateDTO){

        ComputerClub computerClub = computerClubRepository.findById(computerClubUpdateDTO.getId())
                .orElseThrow(() -> new ComputerClubNotFoundException(computerClubUpdateDTO.getId()));


        if(computerClubRepository.existsByNameAndAddress(computerClubUpdateDTO.getName(), computerClubUpdateDTO.getAddress())){

            throw new ComputerClubAllreadyExistsException(computerClubUpdateDTO.getName(), computerClubUpdateDTO.getAddress());
        }

        computerClubMapper.updateComputerClubFromDTO(computerClubUpdateDTO, computerClub);

        return computerClubMapper.toComputerClubResponseDTO(computerClubRepository.save(computerClub));
    }

    public void delete (Long id){

        ComputerClub computerClub = computerClubRepository.findById(id)
                .orElseThrow(() -> new ComputerClubNotFoundException(id));

        computerClubRepository.delete(computerClub);
    }

    public Page<ComputerClubResponseDTO> getAllClubsOnCity(Long cityId, Pageable pageable){

        ComputerClubFilterDTO filterDTO = ComputerClubFilterDTO.builder()
                .cityId(cityId)
                .build();

        return findAll(filterDTO, pageable);
    }
}
