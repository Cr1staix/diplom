package ru.top.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.top.diplom.model.ComputerClub;

public interface ComputerClubRepository extends JpaRepository<ComputerClub, Long>, JpaSpecificationExecutor<ComputerClub> {

    boolean existsByNameAndAddress(String name, String address);
}
