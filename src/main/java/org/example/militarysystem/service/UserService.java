package org.example.militarysystem.service;

import org.example.militarysystem.dto.CommanderDto;
import org.example.militarysystem.dto.UserDto;
import org.example.militarysystem.model.User;
import org.example.militarysystem.repository.UserRepository;
import org.example.militarysystem.utils.userUtils.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user ->new UserDto(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRank(),
                        user.getUnit(),
                        user.getStatus().name(),
                        user.getRole().getRoleName()
                ))
                .collect(Collectors.toList());
    }

    public void approveUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(UserStatus.APPROVED);
        userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public List<CommanderDto> getAllCommanders() {
        return userRepository.findAll().stream()
                .map(user -> new CommanderDto(
                        user.getId(),
                        user.getUsername()))
                .collect(Collectors.toList());
    }


    public UserDto editUser(Integer id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setRank(userDto.getRank());
        user.setUnit(userDto.getUnit());
        user.setStatus(UserStatus.valueOf(userDto.getStatus()));
        user = userRepository.save(user);
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRank(),
                user.getUnit(),
                user.getStatus().name(),
                user.getRole().getRoleName()
        );
    }

    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRank(),
                user.getUnit(),
                user.getStatus().name(),
                user.getRole().getRoleName()
        );
    }

}
