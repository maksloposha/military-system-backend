package org.example.militarysystem.chatEncryption.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "public_keys")
public class PublicKeyEntity {

    @Id
    private String username;

    @Column(length = 2048, nullable = false)
    private String publicKeyJwk;

}
