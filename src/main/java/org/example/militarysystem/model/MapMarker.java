package org.example.militarysystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.militarysystem.utils.mapUtils.MarkerType;
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

    @ManyToOne
    @JoinColumn(name = "unit_type_id")
    private UnitType unitType;

    private String commander;

    private Integer estimatedPersonnel;

    @ManyToOne
    @JoinColumn(name = "position_status_id", nullable = true)
    private PositionStatus positionStatus;
}
