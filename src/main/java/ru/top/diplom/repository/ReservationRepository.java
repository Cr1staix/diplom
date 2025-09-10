package ru.top.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.top.diplom.model.Reservation;

import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.computer.id = :computerId " +
            "AND r.endTime > CURRENT_TIMESTAMP " +
            "ORDER BY r.endTime DESC")
    Optional<Reservation> findActiveByComputerId(@Param("computerId") UUID computerId);
}
