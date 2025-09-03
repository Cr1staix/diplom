package ru.top.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.top.diplom.model.Computer;

import java.util.UUID;

public interface ComputerRepository extends JpaRepository<Computer, UUID>, JpaSpecificationExecutor<Computer> {
}
