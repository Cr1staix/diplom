package ru.top.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.top.diplom.model.ComputerClub;

public interface ComputerClubRepository extends JpaRepository<ComputerClub, Long> {

    boolean existsByNameAndAddress(String name, String address);
}
