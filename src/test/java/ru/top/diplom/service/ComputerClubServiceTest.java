package ru.top.diplom.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubCreateDTO;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubFilterDTO;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubResponseDTO;
import ru.top.diplom.dto.ComputerClubDTO.ComputerClubUpdateDTO;
import ru.top.diplom.exception.city.CityNotFoundException;
import ru.top.diplom.exception.computer_club.ComputerClubAllreadyExistsException;
import ru.top.diplom.exception.computer_club.ComputerClubNotFoundException;
import ru.top.diplom.mapper.ComputerClubMapper;
import ru.top.diplom.mapper.ComputerClubMapperImpl;
import ru.top.diplom.model.City;
import ru.top.diplom.model.ComputerClub;
import ru.top.diplom.repository.CityRepository;
import ru.top.diplom.repository.ComputerClubRepository;
import ru.top.diplom.utils.ComputerClubTestUtils;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ComputerClubService.class, ComputerClubMapperImpl.class})
class ComputerClubServiceTest {

    @Autowired
    private ComputerClubService computerClubService;

    @MockitoBean
    private ComputerClubRepository computerClubRepository;

    @Autowired
    private ComputerClubMapper computerClubMapper;

    @MockitoBean
    private CityRepository cityRepository;

    @Test
    void create_Success_WhenNameAndAddressUnique() {
        //given
        ComputerClubCreateDTO createDTO = ComputerClubTestUtils.createTestComputerClubCreateDTO();
        ComputerClub computerClub = ComputerClubTestUtils.createTestComputerClub();
        City city = ComputerClubTestUtils.createTestCity();

        when(computerClubRepository.existsByNameAndAddress(createDTO.getName(), createDTO.getAddress())).thenReturn(false);
        when(cityRepository.findById(createDTO.getCity_id())).thenReturn(Optional.of(city));
        when(computerClubRepository.save(any(ComputerClub.class))).thenReturn(computerClub);

        //when
        ComputerClubResponseDTO result = computerClubService.create(createDTO);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(computerClub.getName());
        assertThat(result.getAddress()).isEqualTo(computerClub.getAddress());

        verify(computerClubRepository, times(1)).existsByNameAndAddress(createDTO.getName(), createDTO.getAddress());
        verify(cityRepository, times(1)).findById(createDTO.getCity_id());
        verify(computerClubRepository, times(1)).save(any(ComputerClub.class));
    }

    @Test
    void create_Failed_WhenNameAndAddressExists() {
        //given
        ComputerClubCreateDTO createDTO = ComputerClubTestUtils.createTestComputerClubCreateDTO();

        when(computerClubRepository.existsByNameAndAddress(createDTO.getName(), createDTO.getAddress())).thenReturn(true);

        //when then
        assertThatThrownBy(() -> computerClubService.create(createDTO))
                .isInstanceOf(ComputerClubAllreadyExistsException.class);

        verify(computerClubRepository, times(1)).existsByNameAndAddress(createDTO.getName(), createDTO.getAddress());
        verify(cityRepository, times(0)).findById(anyLong());
        verify(computerClubRepository, times(0)).save(any(ComputerClub.class));
    }

    @Test
    void create_Failed_WhenCityNotFound() {
        //given
        ComputerClubCreateDTO createDTO = ComputerClubTestUtils.createTestComputerClubCreateDTO();

        when(computerClubRepository.existsByNameAndAddress(createDTO.getName(), createDTO.getAddress())).thenReturn(false);
        when(cityRepository.findById(createDTO.getCity_id())).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> computerClubService.create(createDTO))
                .isInstanceOf(CityNotFoundException.class);

        verify(computerClubRepository, times(1)).existsByNameAndAddress(createDTO.getName(), createDTO.getAddress());
        verify(cityRepository, times(1)).findById(createDTO.getCity_id());
        verify(computerClubRepository, times(0)).save(any(ComputerClub.class));
    }

    @Test
    void findAll_ReturnsComputerClubs() {
        //given
        ComputerClubFilterDTO filter = ComputerClubTestUtils.createTestComputerClubFilterDTO();
        ComputerClub computerClub = ComputerClubTestUtils.createTestComputerClub();

        int page = 0;
        int size = 5;
        String sort = "id";
        String direction = "ASC";
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));

        Page<ComputerClub> computerClubPage = new PageImpl<>(List.of(computerClub));
        when(computerClubRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(computerClubPage);

        //when
        Page<ComputerClubResponseDTO> result = computerClubService.findAll(filter, pageable);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getId()).isEqualTo(computerClub.getId());
        assertThat(result.getContent().get(0).getName()).isEqualTo(computerClub.getName());

        verify(computerClubRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findById_Success_WhenComputerClubExists() {
        //given
        ComputerClub computerClub = ComputerClubTestUtils.createTestComputerClub();
        when(computerClubRepository.findById(computerClub.getId())).thenReturn(Optional.of(computerClub));

        //when
        ComputerClubResponseDTO result = computerClubService.findById(computerClub.getId());

        //then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(computerClub.getName());
        assertThat(result.getAddress()).isEqualTo(computerClub.getAddress());

        verify(computerClubRepository, times(1)).findById(computerClub.getId());
    }

    @Test
    void findById_Failed_WhenComputerClubNotFound() {
        //given
        Long clubId = 1L;
        when(computerClubRepository.findById(clubId)).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> computerClubService.findById(clubId))
                .isInstanceOf(ComputerClubNotFoundException.class);

        verify(computerClubRepository, times(1)).findById(clubId);
    }

    @Test
    void update_Success_WhenComputerClubExistsAndNameAddressUnique() {
        //given
        ComputerClubUpdateDTO updateDTO = ComputerClubTestUtils.createTestComputerClubUpdateDTO();
        ComputerClub computerClub = ComputerClubTestUtils.createTestComputerClub();

        when(computerClubRepository.findById(updateDTO.getId())).thenReturn(Optional.of(computerClub));
        when(computerClubRepository.existsByNameAndAddress(updateDTO.getName(), updateDTO.getAddress())).thenReturn(false);
        when(computerClubRepository.save(computerClub)).thenReturn(computerClub);

        //when
        ComputerClubResponseDTO result = computerClubService.update(updateDTO);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(updateDTO.getName());
        assertThat(result.getAddress()).isEqualTo(updateDTO.getAddress());

        verify(computerClubRepository, times(1)).findById(updateDTO.getId());
        verify(computerClubRepository, times(1)).existsByNameAndAddress(updateDTO.getName(), updateDTO.getAddress());
        verify(computerClubRepository, times(1)).save(computerClub);
    }

    @Test
    void update_Failed_WhenComputerClubNotFound() {
        //given
        ComputerClubUpdateDTO updateDTO = ComputerClubTestUtils.createTestComputerClubUpdateDTO();
        when(computerClubRepository.findById(updateDTO.getId())).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> computerClubService.update(updateDTO))
                .isInstanceOf(ComputerClubNotFoundException.class);

        verify(computerClubRepository, times(1)).findById(updateDTO.getId());
        verify(computerClubRepository, times(0)).existsByNameAndAddress(anyString(), anyString());
        verify(computerClubRepository, times(0)).save(any(ComputerClub.class));
    }

    @Test
    void update_Failed_WhenNameAndAddressExists() {
        //given
        ComputerClubUpdateDTO updateDTO = ComputerClubTestUtils.createTestComputerClubUpdateDTO();
        ComputerClub computerClub = ComputerClubTestUtils.createTestComputerClub();

        when(computerClubRepository.findById(updateDTO.getId())).thenReturn(Optional.of(computerClub));
        when(computerClubRepository.existsByNameAndAddress(updateDTO.getName(), updateDTO.getAddress())).thenReturn(true);

        //when then
        assertThatThrownBy(() -> computerClubService.update(updateDTO))
                .isInstanceOf(ComputerClubAllreadyExistsException.class);

        verify(computerClubRepository, times(1)).findById(updateDTO.getId());
        verify(computerClubRepository, times(1)).existsByNameAndAddress(updateDTO.getName(), updateDTO.getAddress());
        verify(computerClubRepository, times(0)).save(any(ComputerClub.class));
    }

    @Test
    void delete_Success_WhenComputerClubExists() {
        //given
        ComputerClub computerClub = ComputerClubTestUtils.createTestComputerClub();
        when(computerClubRepository.findById(computerClub.getId())).thenReturn(Optional.of(computerClub));
        doNothing().when(computerClubRepository).delete(computerClub);

        //when
        computerClubService.delete(computerClub.getId());

        //then
        verify(computerClubRepository, times(1)).findById(computerClub.getId());
        verify(computerClubRepository, times(1)).delete(computerClub);
    }

    @Test
    void delete_Failed_WhenComputerClubNotFound() {
        //given
        Long clubId = 1L;
        when(computerClubRepository.findById(clubId)).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> computerClubService.delete(clubId))
                .isInstanceOf(ComputerClubNotFoundException.class);

        verify(computerClubRepository, times(1)).findById(clubId);
        verify(computerClubRepository, times(0)).delete(any(ComputerClub.class));
    }

    @Test
    void getAllClubsOnCity_ReturnsClubs() {
        //given
        Long cityId = 1L;
        ComputerClub computerClub = ComputerClubTestUtils.createTestComputerClub();

        int page = 0;
        int size = 5;
        String sort = "id";
        String direction = "ASC";
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));

        Page<ComputerClub> computerClubPage = new PageImpl<>(List.of(computerClub));
        when(computerClubRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(computerClubPage);

        //when
        Page<ComputerClubResponseDTO> result = computerClubService.getAllClubsOnCity(cityId, pageable);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);

        verify(computerClubRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }
}