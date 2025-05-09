package org.example.militarysystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.militarysystem.security.EncryptedStringAttributeConverter;
import org.example.militarysystem.utils.userUtils.UserStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = EncryptedStringAttributeConverter.class)
    @Column(nullable = false, unique = true)
    private String username;

    @Convert(converter = EncryptedStringAttributeConverter.class)
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Convert(converter = EncryptedStringAttributeConverter.class)
    private String rank;

    @Convert(converter = EncryptedStringAttributeConverter.class)
    private String unit;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.PENDING;
}
