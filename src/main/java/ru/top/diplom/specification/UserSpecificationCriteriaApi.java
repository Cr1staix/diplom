package ru.top.diplom.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.top.diplom.dto.userDTO.UserFilterDTO;
import ru.top.diplom.enums.UserRole;
import ru.top.diplom.enums.UserStatus;
import ru.top.diplom.model.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class UserSpecificationCriteriaApi {
    // Поиск по имени
    private static Specification<User> hasFirstNameLike(String firstName) {
        return (root, query, cb) -> firstName == null ? null :
                cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    // Поиск по фамилии
    private static Specification<User> hasLastNameLike(String lastName) {
        return (root, query, cb) -> lastName == null ? null :
                cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    // Фильтр по номеру телефона
    private static Specification<User> hasPhone(String phone) {
        return (root, query, cb) -> phone == null ? null :
                cb.equal(root.get("phone"), phone);
    }

    // Фильтр по дате рождения (пользователи, родившиеся после указанной даты)
    private static Specification<User> bornAfter(LocalDate date) {
        return (root, query, cb) -> date == null ? null :
                cb.greaterThanOrEqualTo(root.get("dateOfBirth"), date);
    }

    // Фильтр по роли
    private static Specification<User> hasRole(UserRole role) {
        return (root, query, cb) -> role == null ? null :
                cb.equal(root.get("role"), role);
    }

    private static Specification<User> hasStatus(UserStatus status) {
        return (root, query, cb) -> status == null ? null :
                cb.equal(root.get("status"), status);

    }

        // Фильтр по дате создания
        private static Specification<User> addedAfter(LocalDateTime date) {
            return (root, query, cb) -> date == null ? null :
                    cb.greaterThanOrEqualTo(root.get("addedAt"), date);
        }

        // Фильтр по дате обновления
        private static Specification<User> updatedAfter(LocalDateTime date) {
            return (root, query, cb) -> date == null ? null :
                    cb.greaterThanOrEqualTo(root.get("updatedAt"), date);
        }

    // Объединение спецификаций
    @SafeVarargs
    private static Specification<User> combine(Specification<User>... specs) {
        return Arrays.stream(specs)
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse((root, query, cb) -> null);
    }

    // Метод создания спецификации
    public static Specification<User> createSpecification(UserFilterDTO userFilterDTO){

        return UserSpecificationCriteriaApi.combine(
                UserSpecificationCriteriaApi.hasFirstNameLike(userFilterDTO.getFirstName()),
                UserSpecificationCriteriaApi.hasLastNameLike(userFilterDTO.getLastName()),
                UserSpecificationCriteriaApi.hasPhone(userFilterDTO.getPhone()),
                UserSpecificationCriteriaApi.bornAfter(userFilterDTO.getDateOfBirth()),
                UserSpecificationCriteriaApi.hasRole(userFilterDTO.getRole()),
                UserSpecificationCriteriaApi.hasStatus(userFilterDTO.getStatus()),
                UserSpecificationCriteriaApi.addedAfter(userFilterDTO.getAddedAfter()),
                UserSpecificationCriteriaApi.updatedAfter(userFilterDTO.getUpdatedAfter())
        );
    }
}
