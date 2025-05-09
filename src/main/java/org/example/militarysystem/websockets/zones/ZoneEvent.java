package org.example.militarysystem.websockets.zones;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.militarysystem.dto.ZoneDto;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ZoneEvent {
    private String type;
    private ZoneDto zone;
}
