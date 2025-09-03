package ru.top.diplom.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.top.diplom.dto.computerSpecificationDTO.ComputerSpecFilterDTO;
import ru.top.diplom.model.ComputerSpecification;

import java.util.Arrays;
import java.util.Objects;

public class ComputerSpecSpecificationCriteriaApi {

    // Поиск по монитору
    private static Specification<ComputerSpecification> hasMonitorLike(String monitor) {
        return (root, query, cb) -> monitor == null ? null :
                cb.like(cb.lower(root.get("monitor")), "%" + monitor.toLowerCase() + "%");
    }

    // Поиск по клавиатуре
    private static Specification<ComputerSpecification> hasKeyboardLike(String keyboard) {
        return (root, query, cb) -> keyboard == null ? null :
                cb.like(cb.lower(root.get("keyboard")), "%" + keyboard.toLowerCase() + "%");
    }

    // Поиск по мыши
    private static Specification<ComputerSpecification> hasMouseLike(String mouse) {
        return (root, query, cb) -> mouse == null ? null :
                cb.like(cb.lower(root.get("mouse")), "%" + mouse.toLowerCase() + "%");
    }

    // Поиск по наушникам
    private static Specification<ComputerSpecification> hasHeadphonesLike(String headphones) {
        return (root, query, cb) -> headphones == null ? null :
                cb.like(cb.lower(root.get("headphones")), "%" + headphones.toLowerCase() + "%");
    }

    // Поиск по процессору
    private static Specification<ComputerSpecification> hasCpuLike(String cpu) {
        return (root, query, cb) -> cpu == null ? null :
                cb.like(cb.lower(root.get("cpu")), "%" + cpu.toLowerCase() + "%");
    }

    // Поиск по видеокарте
    private static Specification<ComputerSpecification> hasGpuLike(String gpu) {
        return (root, query, cb) -> gpu == null ? null :
                cb.like(cb.lower(root.get("gpu")), "%" + gpu.toLowerCase() + "%");
    }

    // Поиск по оперативной памяти
    private static Specification<ComputerSpecification> hasRamLike(String ram) {
        return (root, query, cb) -> ram == null ? null :
                cb.like(cb.lower(root.get("ram")), "%" + ram.toLowerCase() + "%");
    }

    // Фильтр по ID характеристики
    private static Specification<ComputerSpecification> hasId(Long id) {
        return (root, query, cb) -> id == null ? null :
                cb.equal(root.get("id"), id);
    }

    // Фильтр по количеству связанных компьютеров (минимум)
    private static Specification<ComputerSpecification> hasMinComputersCount(Integer minCount) {
        return (root, query, cb) -> minCount == null ? null :
                cb.ge(cb.size(root.get("computers")), minCount);
    }

    // Фильтр по количеству связанных компьютеров (максимум)
    private static Specification<ComputerSpecification> hasMaxComputersCount(Integer maxCount) {
        return (root, query, cb) -> maxCount == null ? null :
                cb.le(cb.size(root.get("computers")), maxCount);
    }

    // Объединение спецификаций
    @SafeVarargs
    private static Specification<ComputerSpecification> combine(Specification<ComputerSpecification>... specs) {
        return Arrays.stream(specs)
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse((root, query, cb) -> null);
    }

    // Метод создания спецификации
    public static Specification<ComputerSpecification> createSpecification(ComputerSpecFilterDTO filterDTO) {
        return combine(
                hasMonitorLike(filterDTO.getMonitor()),
                hasKeyboardLike(filterDTO.getKeyboard()),
                hasMouseLike(filterDTO.getMouse()),
                hasHeadphonesLike(filterDTO.getHeadphones()),
                hasCpuLike(filterDTO.getCpu()),
                hasGpuLike(filterDTO.getGpu()),
                hasRamLike(filterDTO.getRam()),
                hasId(filterDTO.getId()),
                hasMinComputersCount(filterDTO.getMinComputersCount()),
                hasMaxComputersCount(filterDTO.getMaxComputersCount())
        );
    }
}
