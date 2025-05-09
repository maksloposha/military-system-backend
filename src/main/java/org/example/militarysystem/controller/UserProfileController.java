package org.example.militarysystem.controller;

import org.example.militarysystem.dto.UserDto;
import org.example.militarysystem.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/profile")
public class UserProfileController {

    private final ProfileService profileService;

    public UserProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping()
    public ResponseEntity<UserDto> getProfile() {
        UserDto user = profileService.getCurrentUser();
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/update")
    public ResponseEntity<UserDto> updateProfile(@RequestBody UserDto userDto) {
        UserDto updatedUser = profileService.updateUser(userDto);
        return ResponseEntity.ok(updatedUser);
    }

}
