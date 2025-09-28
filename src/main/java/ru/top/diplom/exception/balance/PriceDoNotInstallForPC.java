package ru.top.diplom.exception.balance;

import java.util.UUID;

public class PriceDoNotInstallForPC extends RuntimeException {

    private static final String MESSAGE = "Цена за час не установлена для компьютера %s";

    public PriceDoNotInstallForPC(UUID computedId) {
        super(MESSAGE.formatted(computedId));
    }
}
