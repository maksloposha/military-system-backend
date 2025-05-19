package org.example.militarysystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.militarysystem.security.EncryptedStringAttributeConverter;

@Getter
@Setter
@Entity
@Table(name = "ranks")
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = EncryptedStringAttributeConverter.class)
    @Column(nullable = false, unique = true)
    private String name;
}
