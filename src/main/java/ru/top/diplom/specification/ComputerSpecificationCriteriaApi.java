package ru.top.diplom.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.top.diplom.dto.computerDTO.ComputerFilterDTO;
import ru.top.diplom.enums.ComputerStatus;
import ru.top.diplom.model.Computer;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class ComputerSpecificationCriteriaApi {

    // Поиск по имени компьютера
    private static Specification<Computer> hasNameLike(String name) {
        return (root, query, cb) -> name == null ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    // Фильтр по статусу компьютера
    private static Specification<Computer> hasStatus(ComputerStatus status) {
        return (root, query, cb) -> status == null ? null :
                cb.equal(root.get("computerStatus"), status);
    }

    // Фильтр по ID спецификации компьютера
    private static Specification<Computer> hasSpecId(Long specId) {
        return (root, query, cb) -> specId == null ? null :
                cb.equal(root.get("computerSpecification").get("id"), specId);
    }

    // Фильтр по названию процессора в спецификации
    private static Specification<Computer> hasCpuLike(String cpu) {
        return (root, query, cb) -> cpu == null ? null :
                cb.like(cb.lower(root.get("computerSpecification").get("cpu")),
                        "%" + cpu.toLowerCase() + "%");
    }

    // Фильтр по объему RAM в спецификации
    private static Specification<Computer> hasRam(Integer ram) {
        return (root, query, cb) -> ram == null ? null :
                cb.equal(root.get("computerSpecification").get("ram"), ram);
    }

    // Фильтр по объему видеопамяти в спецификации
    private static Specification<Computer> hasGpuMemory(Integer gpuMemory) {
        return (root, query, cb) -> gpuMemory == null ? null :
                cb.equal(root.get("computerSpecification").get("gpuMemory"), gpuMemory);
    }

    // Фильтр по названию видеокарты в спецификации
    private static Specification<Computer> hasGpuLike(String gpu) {
        return (root, query, cb) -> gpu == null ? null :
                cb.like(cb.lower(root.get("computerSpecification").get("gpu")),
                        "%" + gpu.toLowerCase() + "%");
    }

    // Фильтр по ID компьютера
    private static Specification<Computer> hasId(UUID id) {
        return (root, query, cb) -> id == null ? null :
                cb.equal(root.get("id"), id);
    }

    // Фильтр по дате создания
    private static Specification<Computer> createdAfter(LocalDateTime date) {
        return (root, query, cb) -> date == null ? null :
                cb.greaterThanOrEqualTo(root.get("createdAt"), date);
    }

    // Фильтр по дате обновления
    private static Specification<Computer> updatedAfter(LocalDateTime date) {
        return (root, query, cb) -> date == null ? null :
                cb.greaterThanOrEqualTo(root.get("updatedAt"), date);
    }


    @SafeVarargs
    private static Specification<Computer> combine(Specification<Computer>... specs) {
        return Arrays.stream(specs)
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse((root, query, cb) -> null);
    }

    // Метод создания спецификации
    public static Specification<Computer> createSpecification(ComputerFilterDTO computerFilterDTO) {
        return ComputerSpecificationCriteriaApi.combine(
                ComputerSpecificationCriteriaApi.hasNameLike(computerFilterDTO.getName()),
                ComputerSpecificationCriteriaApi.hasStatus(computerFilterDTO.getStatus()),
                ComputerSpecificationCriteriaApi.hasSpecId(computerFilterDTO.getSpecId()),
                ComputerSpecificationCriteriaApi.hasCpuLike(computerFilterDTO.getCpu()),
                ComputerSpecificationCriteriaApi.hasRam(computerFilterDTO.getRam()),
                ComputerSpecificationCriteriaApi.hasGpuMemory(computerFilterDTO.getGpuMemory()),
                ComputerSpecificationCriteriaApi.hasGpuLike(computerFilterDTO.getGpu()),
                ComputerSpecificationCriteriaApi.hasId(computerFilterDTO.getId()),
                ComputerSpecificationCriteriaApi.createdAfter(computerFilterDTO.getCreatedAfter()),
                ComputerSpecificationCriteriaApi.updatedAfter(computerFilterDTO.getUpdatedAfter())
        );
    }
}
