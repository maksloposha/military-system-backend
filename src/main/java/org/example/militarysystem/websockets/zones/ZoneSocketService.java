package org.example.militarysystem.websockets.zones;

import org.example.militarysystem.dto.ZoneDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ZoneSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public ZoneSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNewZone(ZoneDto zone) {
        messagingTemplate.convertAndSend("/topic/zones", new ZoneEvent("NEW_ZONE", zone));
    }

    public void sendUpdatedZone(ZoneDto zone) {
        messagingTemplate.convertAndSend("/topic/zones", new ZoneEvent("UPDATE", zone));
    }

    public void sendDeletedZone(ZoneDto zone) {
        messagingTemplate.convertAndSend("/topic/zones", new ZoneEvent("DELETE", zone));
    }
}
