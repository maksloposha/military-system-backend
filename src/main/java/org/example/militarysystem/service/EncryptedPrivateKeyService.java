package org.example.militarysystem.service;


import org.example.militarysystem.dto.EncryptedKeyDto;
import org.example.militarysystem.model.EncryptedPrivateKey;
import org.example.militarysystem.repository.EncryptedPrivateKeyRepository;
import org.springframework.stereotype.Service;

@Service
public class EncryptedPrivateKeyService {

    private final EncryptedPrivateKeyRepository repository;

    public EncryptedPrivateKeyService(EncryptedPrivateKeyRepository repository) {
        this.repository = repository;
    }

    public void saveEncryptedKey(String username, EncryptedKeyDto dto) {
        EncryptedPrivateKey entity = repository.findByUsername(username).orElse(new EncryptedPrivateKey());
        entity.setUsername(username);
        entity.setCiphertext(dto.getCiphertext());
        entity.setIv(dto.getIv());
        entity.setSalt(dto.getSalt());
        repository.save(entity);
    }

    public EncryptedKeyDto getEncryptedKey(String username) {
        return repository.findByUsername(username)
                .map(entity -> {
                    EncryptedKeyDto dto = new EncryptedKeyDto();
                    dto.setCiphertext(entity.getCiphertext());
                    dto.setIv(entity.getIv());
                    dto.setSalt(entity.getSalt());
                    return dto;
                })
                .orElseThrow(() -> new RuntimeException("Key not found for user: " + username));
    }
}
