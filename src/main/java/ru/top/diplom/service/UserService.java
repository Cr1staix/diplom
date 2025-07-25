package ru.top.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.top.diplom.dto.userDTO.UserCreateDTO;
import ru.top.diplom.dto.userDTO.UserResponseDTO;
import ru.top.diplom.dto.userDTO.UserUpdateDTO;
import ru.top.diplom.enums.UserRole;
import ru.top.diplom.enums.UserStatus;
import ru.top.diplom.exception.user.UserAllreadyExistsException;
import ru.top.diplom.exception.user.UserNotFoundException;
import ru.top.diplom.mapper.UserMapper;
import ru.top.diplom.model.User;
import ru.top.diplom.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDTO create (UserCreateDTO userCreateDTO){

        if(userRepository.existsByPhone(userCreateDTO.getPhone())){
            throw new UserAllreadyExistsException(userCreateDTO.getPhone());
        }

        User user = userMapper.toUser(userCreateDTO);

        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.BRONZE);

        return userMapper.toResponseUserDTO(userRepository.save(user));
    }

    public List<UserResponseDTO> findAll () {

        return userRepository.findAll().stream()
                .map(userMapper::toResponseUserDTO)
                .toList();
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
}
