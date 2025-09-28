package ru.top.diplom.utils;

import ru.top.diplom.model.Balance;
import ru.top.diplom.model.User;

import java.math.BigDecimal;

public class BalanceTestUtils {
    public static Balance createTestBalance(User user) {
        return Balance.builder()
                .id(1L)
                .user(user)
                .money(BigDecimal.ZERO)
                .build();
    }

    public static Balance createTestBalanceWithMoney(User user, BigDecimal money) {
        return Balance.builder()
                .id(1L)
                .user(user)
                .money(money)
                .build();
    }
}
