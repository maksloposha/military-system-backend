package org.example.militarysystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.militarysystem.utils.mapUtils.MarkerType;
import org.example.militarysystem.utils.mapUtils.UnitType;
import org.locationtech.jts.geom.Point;


@Getter
@Setter
@Entity
@Table(name = "map_markers")
public class MapMarker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private MarkerType type;

    private String description;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point location;

    @Enumerated(EnumType.STRING)
    private UnitType unitType;

    private String commander;

    private Integer estimatedPersonnel;
}
