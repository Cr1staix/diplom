package ru.top.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.top.diplom.dto.ClubPricingDTO;
import ru.top.diplom.enums.ComputerStatus;
import ru.top.diplom.service.ClubPricingService;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/pricing")
public class ClubPricingController {

    private final ClubPricingService clubPricingService;

    @PutMapping
    public ResponseEntity<ClubPricingDTO> setPriceToPC(@RequestBody ClubPricingDTO clubPricingDTO){

        return ResponseEntity.ok(clubPricingService.setPriceToPC(clubPricingDTO));
    }

    @GetMapping
    public ResponseEntity<BigDecimal> getPriceToPC(@RequestParam Long clubId,
                                                   @RequestParam ComputerStatus computerStatus){

        return ResponseEntity.ok(clubPricingService.getPriceToPC(clubId, computerStatus));
    }

    @GetMapping(path = "{clubId}")
    public ResponseEntity<List<ClubPricingDTO>> getAllPricesForClub(@PathVariable Long clubId){

       return ResponseEntity.ok(clubPricingService.getAllPricesForClub(clubId));
    }
}
