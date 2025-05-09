package org.example.militarysystem.chat.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private String sender;
    private String recipient;
    private String content;
    private LocalDateTime timestamp = LocalDateTime.now();
}
