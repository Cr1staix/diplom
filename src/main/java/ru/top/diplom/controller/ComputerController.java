package ru.top.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.diplom.dto.computerDTO.ComputerCreateDTO;
import ru.top.diplom.dto.computerDTO.ComputerFilterDTO;
import ru.top.diplom.dto.computerDTO.ComputerResponseDTO;
import ru.top.diplom.dto.computerDTO.ComputerUpdateDTO;
import ru.top.diplom.service.ComputerService;
import java.util.UUID;

@RestController
@RequestMapping("api/pc")
@RequiredArgsConstructor
public class ComputerController {

    private final ComputerService computerService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ComputerResponseDTO> create (@RequestBody ComputerCreateDTO computerCreateDTO){

        return ResponseEntity.ok(computerService.create(computerCreateDTO));
    }

    @GetMapping
    public ResponseEntity<Page<ComputerResponseDTO>> findAll(@ModelAttribute ComputerFilterDTO filter,
                                                             @PageableDefault(size = 5, sort = "id",
                                                                     direction = Sort.Direction.ASC) Pageable pageable){

        return ResponseEntity.ok(computerService.findAll(filter, pageable));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ComputerResponseDTO> findById(@PathVariable UUID id){

        return ResponseEntity.ok(computerService.findById(id));
    }

    @PutMapping
    public ResponseEntity<ComputerResponseDTO> update(@RequestBody ComputerUpdateDTO computerUpdateDTO){

        return ResponseEntity.ok(computerService.update(computerUpdateDTO));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){

        computerService.delete(id);

        return ResponseEntity.ok("Компьютер с id - " + id + "удалён");
    }

}
