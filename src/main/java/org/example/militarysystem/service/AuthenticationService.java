package org.example.militarysystem.service;

import org.example.militarysystem.dto.AuthenticationRequest;
import org.example.militarysystem.dto.AuthenticationResponse;
import org.example.militarysystem.dto.RegistrationRequest;
import org.example.militarysystem.dto.RegistrationResponse;
import org.example.militarysystem.exceptions.UserInWrongStatusException;
import org.example.militarysystem.model.Role;
import org.example.militarysystem.model.User;
import org.example.militarysystem.repository.RankRepository;
import org.example.militarysystem.repository.RoleRepository;
import org.example.militarysystem.repository.UnitTypeRepository;
import org.example.militarysystem.repository.UserRepository;
import org.example.militarysystem.security.jwt.JwtTokenUtil;
import org.example.militarysystem.utils.userUtils.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RankRepository rankRepository;
    private final UnitTypeRepository unitTypeRepository;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtTokenUtil jwtTokenUtil,
                                 UserDetailsService userDetailsService, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, RankRepository rankRepository, UnitTypeRepository unitTypeRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.rankRepository = rankRepository;
        this.unitTypeRepository = unitTypeRepository;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        User user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new Exception("User not found"));

        if (user.getStatus() != UserStatus.APPROVED) {
            throw new UserInWrongStatusException(user.getStatus());
        }


        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        final List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new AuthenticationResponse(token, userDetails.getUsername(), roles);
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials", e);
        }
    }

    public RegistrationResponse register(RegistrationRequest request) throws Exception {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new Exception("Username is already taken");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new Exception("Email is already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRank(rankRepository.findByName(request.getRank()));
        user.setUnitType(unitTypeRepository.findByName(request.getUnit()));

        user.setStatus(UserStatus.PENDING);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        Role defaultRole = roleRepository.findByRoleName("USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleName("USER");
                    return roleRepository.save(newRole);
                });
        user.setRole(defaultRole);

        userRepository.save(user);
        return new RegistrationResponse("User registered successfully", user.getStatus().name());
    }
}
