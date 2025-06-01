package org.example.militarysystem.controller;

import org.example.militarysystem.dto.CommanderDto;
import org.example.militarysystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/commanders")
public class CommanderController {

    private final UserService userService;

    public CommanderController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<?> getAllCommanders() {
        try {
            List<CommanderDto> allCommanders = userService.getAllCommanders();
            return ResponseEntity.ok(allCommanders);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch commanders", "details", e.getMessage()));
        }
    }
}
