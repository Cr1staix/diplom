package ru.top.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.top.diplom.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPhone(String phone);
}
