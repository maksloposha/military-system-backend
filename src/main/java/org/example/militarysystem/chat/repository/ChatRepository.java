package org.example.militarysystem.chat.repository;

import org.example.militarysystem.chat.entity.Chat;
import org.example.militarysystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByParticipantsContaining(User currentUser);
}
