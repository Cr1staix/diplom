package ru.top.diplom.exception.club_pricing;

public class ClubPricingNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Цены для клуба не найдены";

    public ClubPricingNotFoundException() {
        super(MESSAGE);
    }
}
