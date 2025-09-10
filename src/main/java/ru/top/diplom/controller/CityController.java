package ru.top.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.diplom.dto.cityDTO.CityCreateDTO;
import ru.top.diplom.dto.cityDTO.CityResponseDTO;
import ru.top.diplom.service.CityService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/cities")
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CityResponseDTO> create (@RequestBody CityCreateDTO cityCreateDTO){

        return ResponseEntity.ok(cityService.create(cityCreateDTO));
    }

    @GetMapping
    public ResponseEntity<List<CityResponseDTO>> findAll(){

        return ResponseEntity.ok(cityService.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<CityResponseDTO> findById(@PathVariable Long id){

        return ResponseEntity.ok(cityService.findById(id));
    }
}
