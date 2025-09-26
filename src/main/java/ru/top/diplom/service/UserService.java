package ru.top.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.diplom.dto.userDTO.SignUpRequest;
import ru.top.diplom.dto.userDTO.UserFilterDTO;
import ru.top.diplom.dto.userDTO.UserResponseDTO;
import ru.top.diplom.dto.userDTO.UserUpdateDTO;
import ru.top.diplom.enums.UserRole;
import ru.top.diplom.enums.UserStatus;
import ru.top.diplom.exception.user.UserAllreadyExistsException;
import ru.top.diplom.exception.user.UserNotFoundException;
import ru.top.diplom.mapper.UserMapper;
import ru.top.diplom.model.Balance;
import ru.top.diplom.model.User;
import ru.top.diplom.repository.UserRepository;
import ru.top.diplom.specification.UserSpecificationCriteriaApi;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final BalanceService balanceService;

    public User signUp (User user){

        if(userRepository.existsByPhone(user.getPhone())){
            throw new UserAllreadyExistsException(user.getPhone());
        }

        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.BRONZE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Balance balance = balanceService.create(user);
        user.setBalance(balance);


        return userRepository.save(user);
    }

    public Page<UserResponseDTO> findAll (UserFilterDTO filter, Pageable pageable) {

        Specification<User> spec = UserSpecificationCriteriaApi.createSpecification(filter);

        return userRepository.findAll(spec, pageable).map(userMapper::toResponseUserDTO);
    }

    public UserResponseDTO findById (Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return userMapper.toResponseUserDTO(user);
    }

    @Transactional
    public UserResponseDTO update(UserUpdateDTO userUpdateDTO){

        User user = userRepository.findById(userUpdateDTO.getId())
                .orElseThrow(() -> new UserNotFoundException(userUpdateDTO.getId()));

        if(userRepository.existsByPhone(userUpdateDTO.getPhone())){
            throw new UserAllreadyExistsException(userUpdateDTO.getPhone());
        }

        userMapper.updateUserFromDTO(userUpdateDTO, user);

        return userMapper.toResponseUserDTO(userRepository.save(user));
    }

    public void delete(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
    }

    public User getByUsername(String phone) {
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new UserNotFoundException(phone));
    }


    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public UserResponseDTO create(SignUpRequest signUpRequest){

        User user = userMapper.toUser(signUpRequest);

        return userMapper.toResponseUserDTO(signUp(user));
    }
}
