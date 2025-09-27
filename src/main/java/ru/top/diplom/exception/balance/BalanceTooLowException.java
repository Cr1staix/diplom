package ru.top.diplom.exception.balance;

import java.math.BigDecimal;

public class BalanceTooLowException extends RuntimeException {

    private static final String MESSAGE = "Недостаточно средств! У вас на балансе %s, необходимая сумма %s";

    public BalanceTooLowException(BigDecimal currentBalance, BigDecimal total) {
        super(MESSAGE.formatted(currentBalance.toPlainString(), total.toPlainString()));
    }
}
