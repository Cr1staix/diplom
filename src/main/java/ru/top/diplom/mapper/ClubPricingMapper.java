package ru.top.diplom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.top.diplom.dto.ClubPricingDTO;
import ru.top.diplom.model.ClubPricing;
import ru.top.diplom.model.ComputerClub;

@Mapper(componentModel = "spring")
public interface ClubPricingMapper {


    ClubPricing toClubPricing (ClubPricingDTO clubPricingDTO);

    ClubPricingDTO toClubPricingDTO (ClubPricing clubPricing);

    default ComputerClub map(Long clubId) {
        return clubId == null ? null : ComputerClub.builder().id(clubId).build();
    }
}
