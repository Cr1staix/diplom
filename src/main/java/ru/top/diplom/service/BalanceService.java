package ru.top.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.diplom.exception.balance.BalanceNotFoundException;
import ru.top.diplom.model.Balance;
import ru.top.diplom.model.User;
import ru.top.diplom.repository.BalanceRepository;
import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final CurrentUserService currentUserService;

    public void create(User user){

        Balance balance = Balance.builder()
                .user(user)
                .build();

        balanceRepository.save(balance);
    }

    @Transactional
    public void deposit(BigDecimal amount){

        User currentUser = currentUserService.findUser();

        Balance balance = balanceRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new BalanceNotFoundException(currentUser.getId()));

        balance.setMoney(balance.getMoney().add(amount));

        balanceRepository.save(balance);
    }

    @Transactional
    public void withdraw(BigDecimal priceOnHours, Integer timesOnHours){

        User currentUser = currentUserService.findUser();

        Balance balance = balanceRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new BalanceNotFoundException(currentUser.getId()));

        BigDecimal totalPrice = priceOnHours.multiply(BigDecimal.valueOf(timesOnHours));

        BigDecimal newBalance = balance.getMoney().subtract(totalPrice);

        balance.setMoney(newBalance);

        balanceRepository.save(balance);
        //TODO УБРАТЬ ИЗ БД В ЮЗЕР поле balance_id, убрать поле из balance club_id!

    }

}
