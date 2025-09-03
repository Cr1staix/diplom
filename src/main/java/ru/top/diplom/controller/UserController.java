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
import ru.top.diplom.dto.userDTO.SignUpRequest;
import ru.top.diplom.dto.userDTO.UserCreateDTO;
import ru.top.diplom.dto.userDTO.UserResponseDTO;
import ru.top.diplom.dto.userDTO.UserUpdateDTO;
import ru.top.diplom.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody SignUpRequest signUpRequest) {

        return ResponseEntity.ok(userService.create(signUpRequest));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {

        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){

        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> update (@RequestBody UserUpdateDTO userUpdateDTO){

        return ResponseEntity.ok(userService.update(userUpdateDTO));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete (@PathVariable Long id){

        userService.delete(id);

        return ResponseEntity.ok("Пользователь с id : " + id + " удалён" );
    }
}
