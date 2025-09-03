package ru.top.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.diplom.dto.userDTO.CurrentUserUpdateDTO;
import ru.top.diplom.dto.userDTO.UserResponseDTO;
import ru.top.diplom.exception.user.UserAllreadyExistsException;
import ru.top.diplom.exception.user.UserNotFoundException;
import ru.top.diplom.mapper.UserMapper;
import ru.top.diplom.model.User;
import ru.top.diplom.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User findUser() {

        return userRepository.findById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())
                .orElseThrow(() -> new UserNotFoundException(findUser().getId()));

    }

    public UserResponseDTO getCurrentUser() {

        User currentUser = findUser();

        return userMapper.toResponseUserDTO(currentUser);
    }

    @Transactional
    public UserResponseDTO update(CurrentUserUpdateDTO currentUserUpdateDTO) {

        User currentUser = findUser();

        if (userRepository.existsByPhone(currentUserUpdateDTO.getPhone())) {
            throw new UserAllreadyExistsException(currentUserUpdateDTO.getPhone());
        }

        userMapper.updateUserFromDTO(currentUserUpdateDTO, currentUser);

        return userMapper.toResponseUserDTO(userRepository.save(currentUser));
    }
}
