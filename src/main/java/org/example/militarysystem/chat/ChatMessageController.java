package org.example.militarysystem.chat;

import org.example.militarysystem.chat.entity.Chat;
import org.example.militarysystem.chat.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatMessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    public ChatMessageController(SimpMessagingTemplate messagingTemplate, ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }

    @MessageMapping("/chat/send")
    public void sendMessage(@Payload ChatMessage message, Principal principal) {
        String sender = principal.getName();
        message.setSender(sender);
        chatService.saveMessage(message);

        message.setContent(message.getEncryptedForRecipient());
        // Надіслати повідомлення одержувачу
        messagingTemplate.convertAndSendToUser(
                message.getRecipient(),
                "/queue/messages",
                message
        );
        message.setContent(message.getEncryptedForSender());
        messagingTemplate.convertAndSendToUser(
                sender,
                "/queue/messages",
                message
        );
    }
}
