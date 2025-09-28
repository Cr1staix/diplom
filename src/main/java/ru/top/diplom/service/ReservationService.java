package ru.top.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.diplom.dto.reservationDTO.ReservationCreateDTO;
import ru.top.diplom.dto.reservationDTO.ReservationResponseDTO;
import ru.top.diplom.exception.balance.PriceDoNotInstallForPC;
import ru.top.diplom.exception.computer.ComputerNotFoundException;
import ru.top.diplom.mapper.ReservationMapper;
import ru.top.diplom.model.Computer;
import ru.top.diplom.model.Reservation;
import ru.top.diplom.model.User;
import ru.top.diplom.repository.ComputerRepository;
import ru.top.diplom.repository.ReservationRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final CurrentUserService currentUserService;
    private final ComputerRepository computerRepository;
    private final  BalanceService balanceService;

    @Transactional
    public ReservationResponseDTO create(ReservationCreateDTO reservationCreateDTO){


        User user = currentUserService.findUser();

        Computer computer = computerRepository.findById(reservationCreateDTO.getComputerId())
                .orElseThrow(() -> new ComputerNotFoundException(reservationCreateDTO.getComputerId()));

        BigDecimal pricePerHour = Optional.ofNullable(computer.getPricePerHour())
                .orElseThrow(() -> new PriceDoNotInstallForPC(computer.getId()));
        long minutes = Duration.between(reservationCreateDTO.getStartTime(), reservationCreateDTO.getEndTime()).toMinutes();
        BigDecimal hours = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

        balanceService.withdraw(pricePerHour, hours);

        Reservation reservation = reservationMapper.toReservation(reservationCreateDTO);

        reservation.setUser(user);

        computer.setIsActive(true);
        computerRepository.save(computer);
        reservation.setComputer(computer);

        return reservationMapper.toResponseDTO(reservationRepository.save(reservation));
    }

    public ReservationResponseDTO findActiveByComputer(UUID computerId) {
        return reservationRepository.findActiveByComputerId(computerId)
                .map(reservationMapper::toResponseDTO)
                .orElse(null);
    }

    @Scheduled(cron = "0 * * * * *")
    public void checkActiveReservation(){

        List<Computer> computers = computerRepository.getActiveComputer().stream()
                .peek(c -> c.setIsActive(false))
                .toList();

        if(!computers.isEmpty()){

            computerRepository.saveAll(computers);
        }
    }
}
