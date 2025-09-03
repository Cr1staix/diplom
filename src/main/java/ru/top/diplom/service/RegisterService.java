package ru.top.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.diplom.dto.RegisterDTO;
import ru.top.diplom.exception.computer_club.ComputerClubNotFoundException;
import ru.top.diplom.exception.user.UserNotFoundException;
import ru.top.diplom.model.ComputerClub;
import ru.top.diplom.model.User;
import ru.top.diplom.repository.ComputerClubRepository;
import ru.top.diplom.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final ComputerClubRepository computerClubRepository;
    private final BalanceService balanceService;

    @Transactional
    public void registerUserToClub(RegisterDTO registerDTO){
        //TODO Изменить ДТО, нам не нужно принимать id usera, он будет поднматься из контекста спринга!

        User user = userRepository.findById(registerDTO.getUserId())
                .orElseThrow(() -> new  UserNotFoundException(registerDTO.getUserId()));

        ComputerClub computerClub = computerClubRepository.findById(registerDTO.getClubId())
                .orElseThrow(() -> new ComputerClubNotFoundException(registerDTO.getClubId()));

        balanceService.create(user, computerClub);

        user.getComputerClubs().add(computerClub);
        userRepository.save(user);
    }
}
