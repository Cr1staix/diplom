package ru.top.diplom.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubFilterDTO;
import ru.top.diplom.model.ComputerClub;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class ComputerClubSpecificationCriteriaApi {
    // Поиск по названию компьютерного клуба
    private static Specification<ComputerClub> hasNameLike(String name) {
        return (root, query, cb) -> name == null ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    // Поиск по адресу
    private static Specification<ComputerClub> hasAddressLike(String address) {
        return (root, query, cb) -> address == null ? null :
                cb.like(cb.lower(root.get("address")), "%" + address.toLowerCase() + "%");
    }

    // Фильтр по ID клуба
    private static Specification<ComputerClub> hasId(Long id) {
        return (root, query, cb) -> id == null ? null :
                cb.equal(root.get("id"), id);
    }

    // Фильтр по дате создания (после указанной даты)
    private static Specification<ComputerClub> addedAfter(LocalDateTime date) {
        return (root, query, cb) -> date == null ? null :
                cb.greaterThanOrEqualTo(root.get("addedAt"), date);
    }

    // Фильтр по дате обновления (после указанной даты)
    private static Specification<ComputerClub> updatedAfter(LocalDateTime date) {
        return (root, query, cb) -> date == null ? null :
                cb.greaterThanOrEqualTo(root.get("updatedAt"), date);
    }

    // Фильтр по ID пользователя (клубы, где есть пользователь)
    private static Specification<ComputerClub> hasUserId(Long userId) {
        return (root, query, cb) -> userId == null ? null :
                cb.isMember(userId, root.get("users").get("id"));
    }

    // Фильтр по ID компьютера (клубы, где есть компьютер)
    private static Specification<ComputerClub> hasComputerId(Long computerId) {
        return (root, query, cb) -> computerId == null ? null :
                cb.isMember(computerId, root.get("computers").get("id"));
    }

    // Фильтр по балансу (клубы с балансом больше или равным)
    private static Specification<ComputerClub> hasBalanceGreaterThanOrEqual(Double amount) {
        return (root, query, cb) -> amount == null ? null :
                cb.greaterThanOrEqualTo(root.get("balance").get("amount"), amount);
    }

    // Фильтр по балансу (клубы с балансом меньше или равным)
    private static Specification<ComputerClub> hasBalanceLessThanOrEqual(Double amount) {
        return (root, query, cb) -> amount == null ? null :
                cb.lessThanOrEqualTo(root.get("balance").get("amount"), amount);
    }

    // Объединение спецификаций
    @SafeVarargs
    private static Specification<ComputerClub> combine(Specification<ComputerClub>... specs) {
        return Arrays.stream(specs)
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse((root, query, cb) -> null);
    }

    // Метод создания спецификации
    public static Specification<ComputerClub> createSpecification(ComputerClubFilterDTO filterDTO) {
        return combine(
                hasNameLike(filterDTO.getName()),
                hasAddressLike(filterDTO.getAddress()),
                hasId(filterDTO.getId()),
                addedAfter(filterDTO.getAddedAfter()),
                updatedAfter(filterDTO.getUpdatedAfter()),
                hasUserId(filterDTO.getUserId()),
                hasComputerId(filterDTO.getComputerId()),
                hasBalanceGreaterThanOrEqual(filterDTO.getMinBalance()),
                hasBalanceLessThanOrEqual(filterDTO.getMaxBalance())
        );
    }
}
