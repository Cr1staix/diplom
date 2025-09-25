package ru.top.diplom.exception.balance;

public class BalanceNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Для юзера с id %d баланс не найден";

    public BalanceNotFoundException(Long userId) {
        super(MESSAGE.formatted(userId));
    }
}
