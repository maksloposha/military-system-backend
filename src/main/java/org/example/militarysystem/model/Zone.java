package org.example.militarysystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.militarysystem.utils.mapUtils.ZoneType;
import org.locationtech.jts.geom.Polygon;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "zones")
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ZoneType type;

    @Column(columnDefinition = "geometry(POLYGON,4326)")
    private Polygon area;

}
