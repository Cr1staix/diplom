package ru.top.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.top.diplom.model.Balance;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
}
