package org.example.militarysystem.controller;

import org.example.militarysystem.service.UserSettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/user/settings")
public class UserSettingsController {

    private final UserSettingService userSettingService;

    public UserSettingsController(UserSettingService userSettingService) {
        this.userSettingService = userSettingService;
    }

     @GetMapping("/get/ranks")
     public ResponseEntity<List<String>> getAllRanks() {
         return ResponseEntity.ok(userSettingService.getAllRanks());
     }

     @GetMapping("/get/unit-types")
     public ResponseEntity<List<String>>  getAllUnitTypes() {
         return ResponseEntity.ok(userSettingService.getAllUnitTypes());
     }

    @PostMapping("/ranks")
    public ResponseEntity<String> createRank(@RequestBody String rank) {
        userSettingService.createRank(rank);
        return ResponseEntity.ok("Rank created successfully");
    }

    @PostMapping("/unit-types")
    public ResponseEntity<String> createUnitType(@RequestBody String unitType) {
        userSettingService.createUnitType(unitType);
        return ResponseEntity.ok("Unit type created successfully");
    }

    @DeleteMapping("/ranks/{id}")
    public ResponseEntity<String> deleteRank(@PathVariable Long id) {
        userSettingService.deleteRank(id);
        return ResponseEntity.ok("Rank deleted successfully");
    }

    @DeleteMapping("/unit-types/{id}")
    public ResponseEntity<String> deleteUnitType(@PathVariable Long id) {
        userSettingService.deleteUnitType(id);
        return ResponseEntity.ok("Unit type deleted successfully");
    }
}
