package org.example.militarysystem.repository;

import org.example.militarysystem.model.EncryptedPrivateKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EncryptedPrivateKeyRepository extends JpaRepository<EncryptedPrivateKey, Long> {
    Optional<EncryptedPrivateKey> findByUsername(String username);
}
