package ru.top.diplom.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import ru.top.diplom.enums.ComputerStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "computer")
public class Computer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Builder.Default
    private Boolean isActive = false;

    @Enumerated(EnumType.STRING)
    private ComputerStatus computerStatus;

    @CreationTimestamp
    private LocalDateTime addedAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;

    @Builder.Default
    private BigDecimal pricePerHour = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "spec_id", nullable = false)
    private ComputerSpecification computerSpecification;

    @ManyToOne
    @JoinColumn(name = "computer_club_id")
    @ToString.Exclude
    private ComputerClub computerClub;

    @OneToMany(mappedBy = "computer", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Reservation> reservations;
}
