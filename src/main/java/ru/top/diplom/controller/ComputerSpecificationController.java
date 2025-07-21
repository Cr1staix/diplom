package ru.top.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.diplom.dto.computerSpecificationDTO.ComputerSpecificationCreateDTO;
import ru.top.diplom.dto.computerSpecificationDTO.ComputerSpecificationResponseDTO;
import ru.top.diplom.dto.computerSpecificationDTO.ComputerSpecificationUpdateDTO;
import ru.top.diplom.service.ComputerSpecificationService;

import java.util.List;

@RestController
@RequestMapping("api/pc_spec")
@RequiredArgsConstructor
public class ComputerSpecificationController {

    private final ComputerSpecificationService computerSpecificationService;

    @PostMapping
    public ResponseEntity<ComputerSpecificationResponseDTO> create (@RequestBody ComputerSpecificationCreateDTO computerSpecificationCreateDTO){

        return ResponseEntity.ok(computerSpecificationService.create(computerSpecificationCreateDTO));
    }

    @GetMapping
    public ResponseEntity<List<ComputerSpecificationResponseDTO>> findAll(){

        return ResponseEntity.ok(computerSpecificationService.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ComputerSpecificationResponseDTO> findById(@PathVariable Long id){

        return ResponseEntity.ok(computerSpecificationService.findById(id));
    }

    @PutMapping
    public ResponseEntity<ComputerSpecificationResponseDTO> update (@RequestBody ComputerSpecificationUpdateDTO computerSpecificationUpdateDTO){

        return ResponseEntity.ok(computerSpecificationService.update(computerSpecificationUpdateDTO));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete (@PathVariable Long id){

        computerSpecificationService.delete(id);

        return ResponseEntity.ok("Спецификация PC с id : " + id + " удалена");
    }
}
