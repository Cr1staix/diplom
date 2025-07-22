package ru.top.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubCreateDTO;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubResponseDTO;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubUpdateDTO;
import ru.top.diplom.service.ComputerClubService;

import java.util.List;

@RestController
@RequestMapping("api/club")
@RequiredArgsConstructor
public class ComputerClubController {

    private final ComputerClubService computerClubService;

    @PostMapping
    public ResponseEntity<ComputerClubResponseDTO> create(@RequestBody ComputerClubCreateDTO computerClubCreateDTO){

        return ResponseEntity.ok(computerClubService.create(computerClubCreateDTO));
    }

    @GetMapping
    public ResponseEntity<List<ComputerClubResponseDTO>> findAll(){

        return ResponseEntity.ok(computerClubService.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ComputerClubResponseDTO> findById(@PathVariable Long id){

        return ResponseEntity.ok(computerClubService.findById(id));
    }

    @PutMapping
    public ResponseEntity<ComputerClubResponseDTO> update (@RequestBody ComputerClubUpdateDTO computerClubUpdateDTO){

        return ResponseEntity.ok(computerClubService.update(computerClubUpdateDTO));
    }

    public ResponseEntity<String> delete (@PathVariable Long id){

        computerClubService.delete(id);

        return ResponseEntity.ok("Компьютерный клуб с id : " + id + " удалён");
    }
}
