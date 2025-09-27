package ru.top.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.diplom.exception.balance.BalanceNotFoundException;
import ru.top.diplom.exception.balance.BalanceTooLowException;
import ru.top.diplom.model.Balance;
import ru.top.diplom.model.User;
import ru.top.diplom.repository.BalanceRepository;
import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final CurrentUserService currentUserService;

    public Balance create(User user){

        Balance balance = Balance.builder()
                .user(user)
                .build();

        return balanceRepository.save(balance);
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
    public void withdraw(BigDecimal priceOnHours, BigDecimal timesOnHours){

        User currentUser = currentUserService.findUser();

        Balance balance = balanceRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new BalanceNotFoundException(currentUser.getId()));

        BigDecimal totalPrice = priceOnHours.multiply(timesOnHours);

        if(currentUser.getBalance().getMoney().compareTo(totalPrice) < 0){

            throw new BalanceTooLowException(currentUser.getBalance().getMoney(), totalPrice);
        }

        BigDecimal newBalance = balance.getMoney().subtract(totalPrice);

        balance.setMoney(newBalance);

        balanceRepository.save(balance);

    }

}
