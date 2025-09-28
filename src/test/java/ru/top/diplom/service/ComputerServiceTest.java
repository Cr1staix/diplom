package ru.top.diplom.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import ru.top.diplom.dto.computerDTO.ComputerCreateDTO;
import ru.top.diplom.dto.computerDTO.ComputerFilterDTO;
import ru.top.diplom.dto.computerDTO.ComputerResponseDTO;
import ru.top.diplom.dto.computerDTO.ComputerUpdateDTO;
import ru.top.diplom.exception.computer.ComputerNotFoundException;
import ru.top.diplom.exception.computer_specification.ComputerSpecificationNotFoundException;
import ru.top.diplom.mapper.ComputerMapper;
import ru.top.diplom.mapper.ComputerMapperImpl;
import ru.top.diplom.model.Computer;
import ru.top.diplom.model.ComputerSpecification;
import ru.top.diplom.repository.ComputerRepository;
import ru.top.diplom.repository.ComputerSpecificationRepository;
import ru.top.diplom.utils.ComputerTestUtils;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ComputerService.class, ComputerMapperImpl.class})
class ComputerServiceTest {

    @Autowired
    private ComputerService computerService;

    @MockitoBean
    private ComputerRepository computerRepository;

    @Autowired
    private ComputerMapper computerMapper;

    @MockitoBean
    private ComputerSpecificationRepository computerSpecificationRepository;

    @Test
    void create_Success_WhenSpecificationExists() {
        //given
        ComputerCreateDTO createDTO = ComputerTestUtils.createTestComputerCreateDTO();
        ComputerSpecification spec = ComputerTestUtils.createTestComputerSpecification();
        Computer computer = ComputerTestUtils.createTestComputer();

        when(computerSpecificationRepository.findById(createDTO.getSpecId())).thenReturn(Optional.of(spec));
        when(computerRepository.save(any(Computer.class))).thenReturn(computer);

        //when
        ComputerResponseDTO result = computerService.create(createDTO);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(computer.getName());

        verify(computerSpecificationRepository, times(1)).findById(createDTO.getSpecId());
        verify(computerRepository, times(1)).save(any(Computer.class));
    }

    @Test
    void create_Failed_WhenSpecificationNotFound() {
        //given
        ComputerCreateDTO createDTO = ComputerTestUtils.createTestComputerCreateDTO();

        when(computerSpecificationRepository.findById(createDTO.getSpecId())).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> computerService.create(createDTO))
                .isInstanceOf(ComputerSpecificationNotFoundException.class);

        verify(computerSpecificationRepository, times(1)).findById(createDTO.getSpecId());
        verify(computerRepository, times(0)).save(any(Computer.class));
    }

    @Test
    void findAll_ReturnsComputers() {
        //given
        ComputerFilterDTO filter = ComputerTestUtils.createTestComputerFilterDTO();
        Computer computer = ComputerTestUtils.createTestComputer();

        int page = 0;
        int size = 5;
        String sort = "id";
        String direction = "ASC";
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));

        Page<Computer> computerPage = new PageImpl<>(List.of(computer));
        when(computerRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(computerPage);

        //when
        Page<ComputerResponseDTO> result = computerService.findAll(filter, pageable);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getId()).isEqualTo(computer.getId());
        assertThat(result.getContent().get(0).getName()).isEqualTo(computer.getName());

        verify(computerRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findById_Success_WhenComputerExists() {
        //given
        Computer computer = ComputerTestUtils.createTestComputer();
        UUID computerId = computer.getId();
        when(computerRepository.findById(computerId)).thenReturn(Optional.of(computer));

        //when
        ComputerResponseDTO result = computerService.findById(computerId);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(computer.getName());
        assertThat(result.getId()).isEqualTo(computer.getId());

        verify(computerRepository, times(1)).findById(computerId);
    }

    @Test
    void findById_Failed_WhenComputerNotFound() {
        //given
        UUID computerId = UUID.randomUUID();
        when(computerRepository.findById(computerId)).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> computerService.findById(computerId))
                .isInstanceOf(ComputerNotFoundException.class);

        verify(computerRepository, times(1)).findById(computerId);
    }

    @Test
    void update_Success_WhenComputerExists() {
        //given
        ComputerUpdateDTO updateDTO = ComputerTestUtils.createTestComputerUpdateDTO();
        updateDTO.setSpecId(null); // устанавливаем specId в null
        Computer computer = ComputerTestUtils.createTestComputer();

        when(computerRepository.findById(updateDTO.getId())).thenReturn(Optional.of(computer));
        when(computerRepository.save(computer)).thenReturn(computer);

        //when
        ComputerResponseDTO result = computerService.update(updateDTO);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(updateDTO.getName());

        verify(computerRepository, times(1)).findById(updateDTO.getId());
        verify(computerSpecificationRepository, times(0)).findById(any());
        verify(computerRepository, times(1)).save(computer);
    }

    @Test
    void update_Success_WhenComputerExistsWithNewSpec() {
        //given
        ComputerUpdateDTO updateDTO = ComputerTestUtils.createTestComputerUpdateDTO();
        updateDTO.setSpecId(2L); // устанавливаем новую спецификацию
        Computer computer = ComputerTestUtils.createTestComputer();
        ComputerSpecification newSpec = ComputerTestUtils.createTestComputerSpecification();
        newSpec.setId(2L);

        when(computerRepository.findById(updateDTO.getId())).thenReturn(Optional.of(computer));
        when(computerSpecificationRepository.findById(updateDTO.getSpecId())).thenReturn(Optional.of(newSpec));
        when(computerRepository.save(computer)).thenReturn(computer);

        //when
        ComputerResponseDTO result = computerService.update(updateDTO);

        //then
        assertThat(result).isNotNull();
        assertThat(computer.getComputerSpecification()).isEqualTo(newSpec);

        verify(computerRepository, times(1)).findById(updateDTO.getId());
        verify(computerSpecificationRepository, times(1)).findById(updateDTO.getSpecId());
        verify(computerRepository, times(1)).save(computer);
    }

    @Test
    void update_Failed_WhenComputerNotFound() {
        //given
        ComputerUpdateDTO updateDTO = ComputerTestUtils.createTestComputerUpdateDTO();
        when(computerRepository.findById(updateDTO.getId())).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> computerService.update(updateDTO))
                .isInstanceOf(ComputerNotFoundException.class);

        verify(computerRepository, times(1)).findById(updateDTO.getId());
        verify(computerRepository, times(0)).save(any(Computer.class));
    }

    @Test
    void update_Failed_WhenSpecificationNotFound() {
        //given
        ComputerUpdateDTO updateDTO = ComputerTestUtils.createTestComputerUpdateDTO();
        updateDTO.setSpecId(2L); // устанавливаем новую спецификацию
        Computer computer = ComputerTestUtils.createTestComputer();

        when(computerRepository.findById(updateDTO.getId())).thenReturn(Optional.of(computer));
        when(computerSpecificationRepository.findById(updateDTO.getSpecId())).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> computerService.update(updateDTO))
                .isInstanceOf(ComputerSpecificationNotFoundException.class);

        verify(computerRepository, times(1)).findById(updateDTO.getId());
        verify(computerSpecificationRepository, times(1)).findById(updateDTO.getSpecId());
        verify(computerRepository, times(0)).save(any(Computer.class));
    }

    @Test
    void delete_Success_WhenComputerExists() {
        //given
        Computer computer = ComputerTestUtils.createTestComputer();
        UUID computerId = computer.getId();
        when(computerRepository.findById(computerId)).thenReturn(Optional.of(computer));
        doNothing().when(computerRepository).delete(computer);

        //when
        computerService.delete(computerId);

        //then
        verify(computerRepository, times(1)).findById(computerId);
        verify(computerRepository, times(1)).delete(computer);
    }

    @Test
    void delete_Failed_WhenComputerNotFound() {
        //given
        UUID computerId = UUID.randomUUID();
        when(computerRepository.findById(computerId)).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> computerService.delete(computerId))
                .isInstanceOf(ComputerNotFoundException.class);

        verify(computerRepository, times(1)).findById(computerId);
        verify(computerRepository, times(0)).delete(any(Computer.class));
    }
}