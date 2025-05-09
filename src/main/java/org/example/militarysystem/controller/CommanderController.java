package org.example.militarysystem.controller;

import org.example.militarysystem.dto.CommanderDto;
import org.example.militarysystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/commanders")
public class CommanderController {

    private final UserService userService;

    public CommanderController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<CommanderDto>> getAllCommanders() {
        List<CommanderDto> allCommanders = userService.getAllCommanders();
        return ResponseEntity.ok(allCommanders);
    }
}
