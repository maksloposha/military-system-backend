package org.example.militarysystem.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.militarysystem.utils.mapUtils.UnitType;

import java.time.LocalDate;

@Entity
@Table(name = "military_unit_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MilitaryUnitDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UnitType unitType;

    private String supplyLevel;

    private LocalDate lastRotation;

    private Integer personnelCount;

    @OneToOne
    @JoinColumn(name = "commander_id")
    private User commander;

    @OneToOne
    @JoinColumn(name = "map_marker_id")
    private MapMarker mapMarker;
}
