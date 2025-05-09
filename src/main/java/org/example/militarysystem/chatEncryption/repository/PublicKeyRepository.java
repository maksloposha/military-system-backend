package org.example.militarysystem.chatEncryption.repository;

import org.example.militarysystem.chatEncryption.model.PublicKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicKeyRepository extends JpaRepository<PublicKeyEntity, String> {
}
