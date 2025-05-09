package org.example.militarysystem.chatEncryption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/keys")
public class PublicKeyController {

    private final PublicKeyService keyService;

    @Autowired
    public PublicKeyController(PublicKeyService keyService) {
        this.keyService = keyService;
    }

    @PostMapping
    public ResponseEntity<Void> uploadKey(@RequestBody PublicKeyDto keyDto,
                                          Principal principal) {
        keyService.savePublicKey(principal.getName(), keyDto.getJwk());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<PublicKeyDto> getKey(@PathVariable String username) {
        return keyService.getPublicKeyForUser(username)
            .map(jwk -> ResponseEntity.ok(new PublicKeyDto(jwk)))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public ResponseEntity<PublicKeyDto> getMyKey() {
        return keyService.getMyPublicKey()
            .map(jwk -> ResponseEntity.ok(new PublicKeyDto(jwk)))
            .orElse(ResponseEntity.notFound().build());
    }
}
