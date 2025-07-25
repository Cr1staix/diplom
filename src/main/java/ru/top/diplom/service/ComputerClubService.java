package ru.top.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubCreateDTO;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubResponseDTO;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubUpdateDTO;
import ru.top.diplom.exception.computer_club.ComputerClubAllreadyExistsException;
import ru.top.diplom.exception.computer_club.ComputerClubNotFoundException;
import ru.top.diplom.mapper.ComputerClubMapper;
import ru.top.diplom.model.ComputerClub;
import ru.top.diplom.repository.ComputerClubRepository;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ComputerClubService {

    private final ComputerClubRepository computerClubRepository;
    private final ComputerClubMapper computerClubMapper;

    public ComputerClubResponseDTO create (ComputerClubCreateDTO computerClubCreateDTO){

        if(computerClubRepository.existsByNameAndAddress(computerClubCreateDTO.getName(), computerClubCreateDTO.getAddress())){

            throw new ComputerClubAllreadyExistsException(computerClubCreateDTO.getName(), computerClubCreateDTO.getAddress());
        }

        ComputerClub computerClub = computerClubMapper.toComputerClub(computerClubCreateDTO);

        computerClubRepository.save(computerClub);

        return computerClubMapper.toComputerClubResponseDTO(computerClub);
    }

    public List<ComputerClubResponseDTO> findAll(){

      return   computerClubRepository.findAll().stream()
                .map(computerClubMapper::toComputerClubResponseDTO)
                .toList();
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
}
