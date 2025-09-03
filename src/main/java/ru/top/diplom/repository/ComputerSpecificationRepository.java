package ru.top.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.top.diplom.model.ComputerSpecification;

public interface ComputerSpecificationRepository extends JpaRepository<ComputerSpecification, Long>, JpaSpecificationExecutor<ComputerSpecification> {
}
