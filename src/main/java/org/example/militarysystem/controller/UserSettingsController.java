package org.example.militarysystem.controller;

import org.example.militarysystem.model.UnitType;
import org.example.militarysystem.service.UserSettingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user/settings")
public class UserSettingsController {

    private final UserSettingService userSettingService;

    public UserSettingsController(UserSettingService userSettingService) {
        this.userSettingService = userSettingService;
    }

    @GetMapping("/get/ranks")
    public ResponseEntity<?> getAllRanks() {
        try {
            return ResponseEntity.ok(userSettingService.getAllRanks());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch ranks", "details", e.getMessage()));
        }
    }

    @GetMapping("/get/unit-types")
    public ResponseEntity<?> getAllUnitTypes() {
        try {
            return ResponseEntity.ok(userSettingService.getAllUnitTypes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch unit types", "details", e.getMessage()));
        }
    }

    @PostMapping("/ranks")
    public ResponseEntity<?> createRank(@RequestBody String rank) {
        try {
            userSettingService.createRank(rank);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Rank created successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid rank data", "details", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create rank", "details", e.getMessage()));
        }
    }

    @PostMapping("/unit-types")
    public ResponseEntity<?> createUnitType(@RequestBody UnitType unitType) {
        try {
            userSettingService.createUnitType(unitType);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Unit type created successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid unit type data", "details", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create unit type", "details", e.getMessage()));
        }
    }

    @DeleteMapping("/ranks/{name}")
    public ResponseEntity<?> deleteRank(@PathVariable String name) {
        try {
            userSettingService.deleteRank(name);
            return ResponseEntity.ok(Map.of("message", "Rank deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Rank not found", "details", e.getMessage()));
        }
    }

    @DeleteMapping("/unit-types/{id}")
    public ResponseEntity<?> deleteUnitType(@PathVariable Long id) {
        try {
            userSettingService.deleteUnitType(id);
            return ResponseEntity.ok(Map.of("message", "Unit type deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Unit type not found", "details", e.getMessage()));
        }
    }

    @GetMapping("/position-statuses")
    public ResponseEntity<?> getAllPositionStatuses() {
        try {
            return ResponseEntity.ok(userSettingService.getAllPositionStatuses());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch position statuses", "details", e.getMessage()));
        }
    }

    @PostMapping("/position-statuses")
    public ResponseEntity<?> createPositionStatus(@RequestBody String positionStatus) {
        try {
            userSettingService.createPositionStatus(positionStatus);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Position status created successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid position status data", "details", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create position status", "details", e.getMessage()));
        }
    }

    @DeleteMapping("/position-statuses/{name}")
    public ResponseEntity<?> deletePositionStatus(@PathVariable String name) {
        try {
            userSettingService.deletePositionStatus(name);
            return ResponseEntity.ok(Map.of("message", "Position status deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Position status not found", "details", e.getMessage()));
        }
    }
}
