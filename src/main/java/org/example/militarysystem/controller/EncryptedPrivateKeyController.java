package org.example.militarysystem.controller;


import org.example.militarysystem.dto.EncryptedKeyDto;
import org.example.militarysystem.service.EncryptedPrivateKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/keys/encrypted")
public class EncryptedPrivateKeyController {

    private final EncryptedPrivateKeyService service;

    public EncryptedPrivateKeyController(EncryptedPrivateKeyService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<EncryptedKeyDto> getEncryptedKey(Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(service.getEncryptedKey(username));
    }

    @PostMapping
    public ResponseEntity<Void> saveEncryptedKey(@RequestBody EncryptedKeyDto dto, Principal principal) {
        String username = principal.getName();
        service.saveEncryptedKey(username, dto);
        return ResponseEntity.ok().build();
    }
}
