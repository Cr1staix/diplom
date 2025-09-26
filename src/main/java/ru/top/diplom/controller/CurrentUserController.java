package ru.top.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.diplom.dto.userDTO.CurrentUserUpdateDTO;
import ru.top.diplom.dto.userDTO.UserResponseDTO;
import ru.top.diplom.service.CurrentUserService;

@RestController
@RequestMapping("api/me")
@RequiredArgsConstructor
public class CurrentUserController {

    private final CurrentUserService currentUserService;

    @GetMapping
    public ResponseEntity<UserResponseDTO> getCurrentUser(){

        return ResponseEntity.ok(currentUserService.getCurrentUser());
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> update (@RequestBody CurrentUserUpdateDTO currentUserUpdateDTO){

        return ResponseEntity.ok(currentUserService.update(currentUserUpdateDTO));
    }

}