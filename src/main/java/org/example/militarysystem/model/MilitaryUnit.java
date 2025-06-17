package org.example.militarysystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class MilitaryUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "commander_id")
    private User commander;

    @OneToOne
    private MilitaryUnit parentUnit;

    @ManyToOne
    @JoinColumn(name = "military_formation_id", nullable = false)
    private MilitaryFormation militaryFormation;
}
