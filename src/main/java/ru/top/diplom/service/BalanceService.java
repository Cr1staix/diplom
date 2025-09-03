package ru.top.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.top.diplom.model.Balance;
import ru.top.diplom.model.ComputerClub;
import ru.top.diplom.model.User;
import ru.top.diplom.repository.BalanceRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;

    public void create(User user, ComputerClub computerClub){

        Balance balance = Balance.builder()
                .user(user)
                .computerClub(computerClub)
                .build();

        balanceRepository.save(balance);
    }

}
