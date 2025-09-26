package ru.top.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.top.diplom.enums.ComputerStatus;
import ru.top.diplom.model.ClubPricing;

import java.util.Optional;

public interface ClubPricingRepository extends JpaRepository<ClubPricing, Long> {

    Optional<ClubPricing> findByClubIdAndComputerStatus(Long clubId, ComputerStatus status);
}
