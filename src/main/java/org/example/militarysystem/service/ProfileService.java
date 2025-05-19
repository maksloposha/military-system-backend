package org.example.militarysystem.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.militarysystem.dto.UserDto;
import org.example.militarysystem.model.User;
import org.example.militarysystem.repository.UserRepository;
import org.example.militarysystem.security.jwt.JwtTokenUtil;
import org.example.militarysystem.utils.userUtils.UserStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public ProfileService(UserRepository userRepository, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public UserDto getCurrentUser() {
        User user = getCurrentUserEntity();
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRank() == null ? null : user.getRank().getName(),
                user.getUnitType() == null ? null : user.getUnitType().getName(),
                user.getStatus().name(),
                user.getRole().getRoleName()
        );
    }

    public User getCurrentUserEntity() {
        return userRepository.findByUsername(getUsernameFromToken())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private String getToken() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();
        return jwtTokenUtil.getTokenFromCookie(request);
    }

    public UserDto updateUser(UserDto userDto) {
        String username = getUsernameFromToken();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!Objects.equals(user.getId(), userDto.getId())) {
            throw new RuntimeException("You can only update your own profile");
        }
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setStatus(UserStatus.valueOf(userDto.getStatus()));
        user = userRepository.save(user);
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRank().getName(),
                user.getUnitType().getName(),
                user.getStatus().name(),
                user.getRole().getRoleName()
        );
    }


    private String getUsernameFromToken() {
        return jwtTokenUtil.getUsernameFromToken(getToken());
    }
}
