package org.example.militarysystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.militarysystem.dto.UserDto;
import org.example.militarysystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<Void> approveUser(@PathVariable Integer id) {
        userService.approveUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<UserDto> editUser(@PathVariable Integer id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.editUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<String>> getAvailableUsernames() {
        List<UserDto> allUsers = userService.getAllUsers();
        List<String> usernames = allUsers.stream()
                .map(UserDto::getUsername)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usernames);
    }

}
