package ru.top.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.top.diplom.model.City;

public interface CityRepository extends JpaRepository<City, Long> {

    boolean existsByName( String name);
}
