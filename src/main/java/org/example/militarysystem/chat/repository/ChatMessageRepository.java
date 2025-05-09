package org.example.militarysystem.chat.repository;

import org.example.militarysystem.chat.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findBySenderAndRecipientOrderByTimestamp(String sender, String recipient);
}
