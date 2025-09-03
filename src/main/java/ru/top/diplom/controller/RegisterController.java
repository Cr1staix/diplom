package ru.top.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.diplom.dto.RegisterDTO;
import ru.top.diplom.service.RegisterService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/register")
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping
    public ResponseEntity<String> registerUserToClub(@RequestBody RegisterDTO registerDTO){

        registerService.registerUserToClub(registerDTO);

        return ResponseEntity.ok("Вы успешно зарегистрировались");
    }
}
