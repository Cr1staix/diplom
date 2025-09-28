package ru.top.diplom.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.top.diplom.dto.userDTO.SignUpRequest;
import ru.top.diplom.dto.userDTO.UserFilterDTO;
import ru.top.diplom.dto.userDTO.UserResponseDTO;
import ru.top.diplom.dto.userDTO.UserUpdateDTO;
import ru.top.diplom.exception.user.UserAllreadyExistsException;
import ru.top.diplom.exception.user.UserNotFoundException;
import ru.top.diplom.mapper.UserMapper;
import ru.top.diplom.mapper.UserMapperImpl;
import ru.top.diplom.model.Balance;
import ru.top.diplom.model.User;
import ru.top.diplom.repository.UserRepository;
import ru.top.diplom.utils.UserTestUtils;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserService.class, UserMapperImpl.class})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private BalanceService balanceService;

    @Test
    void findAll_ReturnsUsers() {
        //given
        User user = UserTestUtils.createTestUser();
        UserFilterDTO filter = UserTestUtils.createUserFilterDTO();

        int page = 0;
        int size = 5;
        String sort = "id";
        String direction = "ASC";
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));

        Page<User> userPage = new PageImpl<>(List.of(user));
        when(userRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(userPage);
        UserResponseDTO userResponseDTO = userMapper.toResponseUserDTO(user);

        //when
        Page<UserResponseDTO> result = userService.findAll(filter, pageable);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getId()).isEqualTo(user.getId());
        assertThat(result.getContent().get(0).getFirstName()).isEqualTo(user.getFirstName());
        assertThat(result.getContent().get(0).getLastName()).isEqualTo(user.getLastName());
        assertThat(user.getId()).isEqualTo(userResponseDTO.getId());
        assertThat(user.getFirstName()).isEqualTo(userResponseDTO.getFirstName());

        verify(userRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void signUp_Success_WhenPhoneIsUnique() {
        //given
        SignUpRequest signUpRequest = UserTestUtils.createTestUserCreateDTO();
        User userForMapping = userMapper.toUser(signUpRequest);
        UserResponseDTO userResponseDTO = userMapper.toResponseUserDTO(userForMapping);
        Balance balance = new Balance(); // создаем тестовый баланс

        when(userRepository.existsByPhone(signUpRequest.getPhone())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(balanceService.create(userForMapping)).thenReturn(balance);
        when(userRepository.save(userForMapping)).thenReturn(userForMapping);

        //when
        UserResponseDTO result = userService.create(signUpRequest);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getPhone()).isEqualTo(userForMapping.getPhone());
        assertThat(result.getPhone()).isEqualTo(signUpRequest.getPhone());
        assertThat(userResponseDTO.getPhone()).isEqualTo(userForMapping.getPhone());

        verify(userRepository, times(1)).existsByPhone(signUpRequest.getPhone());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(balanceService, times(1)).create(userForMapping);
        verify(userRepository, times(2)).save(userForMapping); // два раза из-за двух вызовов save в signUp
    }

    @Test
    void signUp_Failed_WhenPhoneExists() {
        //given
        SignUpRequest signUpRequest = UserTestUtils.createTestUserCreateDTO();
        User user = UserTestUtils.createTestUser();
        when(userRepository.existsByPhone(signUpRequest.getPhone())).thenReturn(true);

        //when then
        assertThatThrownBy(() -> userService.create(signUpRequest))
                .isInstanceOf(UserAllreadyExistsException.class);

        verify(userRepository, times(1)).existsByPhone(signUpRequest.getPhone());
        verify(userRepository, times(0)).save(user);
    }

    @Test
    void delete_Success_WhenUserExists() {
        //given
        User user = UserTestUtils.createTestUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        //when
        userService.delete(user.getId());

        //then
        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void delete_Failed_WhenUserNotFound() {
        //given
        User user = UserTestUtils.createTestUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> userService.delete(user.getId()))
                .isInstanceOf(UserNotFoundException.class);

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(0)).delete(user);
    }

    @Test
    void update_Success_WhenUserExistsAndPhoneIsUnique() {
        //given
        User user = UserTestUtils.createTestUser();
        UserUpdateDTO userUpdateDTO = UserTestUtils.createTestUserUpdateDTO();
        User userForMapping = new User();
        userMapper.updateUserFromDTO(userUpdateDTO, userForMapping);
        UserResponseDTO userResponseDTO = userMapper.toResponseUserDTO(userForMapping);

        when(userRepository.findById(userUpdateDTO.getId())).thenReturn(Optional.of(user));
        when(userRepository.existsByPhone(userUpdateDTO.getPhone())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        //when
        UserResponseDTO result = userService.update(userUpdateDTO);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getPhone()).isEqualTo(userUpdateDTO.getPhone());
        assertThat(result.getPhone()).isEqualTo(userForMapping.getPhone());
        assertThat(userResponseDTO.getPhone()).isEqualTo(userUpdateDTO.getPhone());

        verify(userRepository, times(1)).findById(userUpdateDTO.getId());
        verify(userRepository, times(1)).existsByPhone(userUpdateDTO.getPhone());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void update_Failed_WhenUserNotFound() {
        //given
        User user = UserTestUtils.createTestUser();
        UserUpdateDTO userUpdateDTO = UserTestUtils.createTestUserUpdateDTO();
        when(userRepository.findById(userUpdateDTO.getId())).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> userService.update(userUpdateDTO))
                .isInstanceOf(UserNotFoundException.class);

        verify(userRepository, times(1)).findById(userUpdateDTO.getId());
        verify(userRepository, times(0)).existsByPhone(userUpdateDTO.getPhone());
        verify(userRepository, times(0)).save(user);
    }

    @Test
    void update_Failed_WhenPhoneExists() {
        //given
        User user = UserTestUtils.createTestUser();
        UserUpdateDTO userUpdateDTO = UserTestUtils.createTestUserUpdateDTO();
        when(userRepository.findById(userUpdateDTO.getId())).thenReturn(Optional.of(user));
        when(userRepository.existsByPhone(userUpdateDTO.getPhone())).thenReturn(true);

        //when then
        assertThatThrownBy(() -> userService.update(userUpdateDTO))
                .isInstanceOf(UserAllreadyExistsException.class);

        verify(userRepository, times(1)).findById(userUpdateDTO.getId());
        verify(userRepository, times(1)).existsByPhone(userUpdateDTO.getPhone());
        verify(userRepository, times(0)).save(user);
    }

    @Test
    void findById_Success_WhenUserExists() {
        //given
        User user = UserTestUtils.createTestUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        UserResponseDTO userResponseDTO = userMapper.toResponseUserDTO(user);

        //when
        UserResponseDTO result = userService.findById(user.getId());

        //then
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(result.getLastName()).isEqualTo(user.getLastName());
        assertThat(userResponseDTO.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userResponseDTO.getLastName()).isEqualTo(user.getLastName());

        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void findById_Fails_WhenUserNotFound() {
        //given
        User user = UserTestUtils.createTestUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> userService.findById(user.getId()))
                .isInstanceOf(UserNotFoundException.class);

        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void getByUsername_Success_WhenUserExists() {
        //given
        User user = UserTestUtils.createTestUser();
        String phone = user.getPhone();
        when(userRepository.findByPhone(phone)).thenReturn(Optional.of(user));

        //when
        User result = userService.getByUsername(phone);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getPhone()).isEqualTo(phone);
        assertThat(result.getFirstName()).isEqualTo(user.getFirstName());

        verify(userRepository, times(1)).findByPhone(phone);
    }

    @Test
    void getByUsername_Fails_WhenUserNotFound() {
        //given
        String phone = "88005353535";
        when(userRepository.findByPhone(phone)).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> userService.getByUsername(phone))
                .isInstanceOf(UserNotFoundException.class);

        verify(userRepository, times(1)).findByPhone(phone);
    }

    @Test
    void userDetailsService_ReturnsUserDetailsService() {
        //when
        UserDetailsService userDetailsService = userService.userDetailsService();

        //then
        assertThat(userDetailsService).isNotNull();
    }
}