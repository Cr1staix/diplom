package ru.top.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.top.diplom.repository.ClubPricingRepository;

@Service
@RequiredArgsConstructor
public class ClubPricingService {

    private final ClubPricingRepository clubPricingRepository;
}
