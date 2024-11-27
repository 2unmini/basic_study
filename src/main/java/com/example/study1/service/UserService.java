package com.example.study1.service;

import com.example.study1.dto.ReadUserResponseDTO;
import com.example.study1.dto.UpdateUserResponseDTO;
import com.example.study1.dto.UserDTO;
import com.example.study1.entity.User;
import com.example.study1.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(UserDTO userDto){
        User user = new User(userDto);
        return userRepository.save(user);
    }
    @Transactional
    public String login(UserDTO userDto)  {
        User user = userRepository.findByName(userDto.getName()).orElseThrow(EntityNotFoundException::new);
        if(!user.getPassword().equals(userDto.getPassword())){
            throw new RuntimeException();
        }
        user.createUUID();

        return user.getUuid();
    }

    public User checkUserBySession(String uuid) {
        return userRepository.findByUuid(uuid).orElseThrow(EntityNotFoundException::new);

    }

    public List<ReadUserResponseDTO> findUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(ReadUserResponseDTO::toDto).toList();
    }
    @Transactional
    public UpdateUserResponseDTO updateUser(UserDTO updateUserDto, String uuid) {

        User user = userRepository.findByUuid(uuid).orElseThrow(EntityNotFoundException::new);
        user.updateUserInfo(updateUserDto);
        return new UpdateUserResponseDTO(user);
    }

    public void deleteUser(String uuid) {
        User user = userRepository.findByUuid(uuid).orElseThrow(EntityNotFoundException::new);
        userRepository.delete(user);


    }
}
