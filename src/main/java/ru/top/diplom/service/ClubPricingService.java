package ru.top.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.top.diplom.dto.ClubPricingDTO;
import ru.top.diplom.enums.ComputerStatus;
import ru.top.diplom.exception.balance.BalanceNotFoundException;
import ru.top.diplom.exception.club_pricing.ClubPricingNotFoundException;
import ru.top.diplom.mapper.ClubPricingMapper;
import ru.top.diplom.model.ClubPricing;
import ru.top.diplom.repository.ClubPricingRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubPricingService {

    private final ClubPricingRepository clubPricingRepository;
    private final ClubPricingMapper clubPricingMapper;

    public ClubPricingDTO setPriceToPC (ClubPricingDTO clubPricingDTO){

        ClubPricing clubPricing = clubPricingRepository.findByClubIdAndComputerStatus(clubPricingDTO.getClubId(), clubPricingDTO.getComputerStatus())
                .orElse(clubPricingMapper.toClubPricing(clubPricingDTO));

        clubPricing.setPricePerHour(clubPricingDTO.getPricePerHour());

        return clubPricingMapper.toClubPricingDTO(clubPricingRepository.save(clubPricing));

    }

    public BigDecimal getPriceToPC(Long clubId, ComputerStatus computerStatus){

        return clubPricingRepository.findByClubIdAndComputerStatus(clubId, computerStatus)
                .map(ClubPricing::getPricePerHour)
                .orElseThrow(ClubPricingNotFoundException::new);
    }

    public List<ClubPricingDTO> getAllPricesForClub(Long clubId){

        return clubPricingRepository.findAllByClubId(clubId)
                .stream().map(clubPricingMapper::toClubPricingDTO)
                .toList();
    }
}
