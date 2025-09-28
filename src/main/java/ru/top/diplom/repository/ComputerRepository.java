package ru.top.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.top.diplom.model.Computer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComputerRepository extends JpaRepository<Computer, UUID>, JpaSpecificationExecutor<Computer> {

    @Query(value = "SELECT computer.* FROM computer " +
            "JOIN reservation ON computer.id = reservation.computer_id " +
            "WHERE computer.is_active = true " +
            "AND reservation.end_time >= CURRENT_TIMESTAMP",
            nativeQuery = true)
    List<Computer> getActiveComputer();
}
