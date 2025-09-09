package ru.top.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.top.diplom.dto.cityDTO.CityCreateDTO;
import ru.top.diplom.dto.cityDTO.CityResponseDTO;
import ru.top.diplom.exception.city.CityAlreadyExistsException;
import ru.top.diplom.exception.city.CityNotFoundException;
import ru.top.diplom.mapper.CityMapper;
import ru.top.diplom.model.City;
import ru.top.diplom.repository.CityRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public CityResponseDTO create (CityCreateDTO cityCreateDTO){

        if(cityRepository.existsByName(cityCreateDTO.getName())){

            throw new CityAlreadyExistsException(cityCreateDTO.getName());
        }

        City city = cityMapper.toCity(cityCreateDTO);

        return cityMapper.toResponseDTO(cityRepository.save(city));
    }

    public List<CityResponseDTO> findAll(){

        return cityRepository.findAll().stream()
                .map(cityMapper::toResponseDTO)
                .toList();
    }

    public CityResponseDTO findById (Long id){

        City city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));

        return cityMapper.toResponseDTO(city);
    }
}
