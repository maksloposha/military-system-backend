package org.example.militarysystem.chat;

import org.example.militarysystem.chat.dto.ChatDTO;
import org.example.militarysystem.chat.dto.ChatMessageDTO;
import org.example.militarysystem.chat.entity.Chat;
import org.example.militarysystem.chat.entity.ChatMessage;
import org.example.militarysystem.chat.repository.ChatMessageRepository;
import org.example.militarysystem.chat.repository.ChatRepository;
import org.example.militarysystem.dto.UserDto;
import org.example.militarysystem.model.User;
import org.example.militarysystem.repository.UserRepository;
import org.example.militarysystem.service.ProfileService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ProfileService profileService;

    public ChatService(ChatRepository chatRepository, ChatMessageRepository chatMessageRepository, UserRepository userRepository, ProfileService profileService) {
        this.chatRepository = chatRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.userRepository = userRepository;
        this.profileService = profileService;
    }

    public void saveChat(ChatDTO chatDTO) {
        Chat chat = new Chat();
        chat.setName(chatDTO.getName());
        List<User> participants = chatDTO.getParticipants().stream()
                .map(username -> userRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User not found: " + username)))
                .collect(Collectors.toList());
        chat.setParticipants(participants);
        chatRepository.save(chat);
    }

    public List<Chat> getAllChatsOfCurrentUser() {
        User currentUser = profileService.getCurrentUserEntity();
        return chatRepository.findByParticipantsContaining(currentUser);
    }

    public void deleteChat(Long id) {
        chatRepository.deleteById(id);
    }

    public Chat getChat(Long id) {
        return chatRepository.findById(id).orElse(null);
    }

    public List<User> getUsersWithoutChatWith() {
        User currentUser = profileService.getCurrentUserEntity();
        List<Chat> userChats = chatRepository.findByParticipantsContaining(currentUser);

        Set<User> alreadyChattedWith = userChats.stream()
                .flatMap(chat -> chat.getParticipants().stream())
                .filter(user -> !user.getId().equals(currentUser.getId()))
                .collect(Collectors.toSet());

        return userRepository.findAll().stream()
                .filter(user -> !user.getId().equals(currentUser.getId()))
                .filter(user -> !alreadyChattedWith.contains(user))
                .collect(Collectors.toList());
    }

    public List<ChatMessageDTO> getMessagesWithRecipient(String recipient) {
        User currentUser = profileService.getCurrentUserEntity();
        String currentUsername = currentUser.getUsername();

        List<ChatMessage> messagesFrom = chatMessageRepository.findBySenderAndRecipientOrderByTimestamp(currentUsername, recipient);
        List<ChatMessage> messagesTo = chatMessageRepository.findBySenderAndRecipientOrderByTimestamp(recipient, currentUsername);

        List<ChatMessage> messages = new ArrayList<>(messagesFrom);
        messages.addAll(messagesTo);

        return messages.stream()
                .map(message -> {
                    String contentToShow;

                    if (message.getSender().equals(currentUsername)) {
                        contentToShow = message.getEncryptedForSender();
                    } else {
                        contentToShow = message.getEncryptedForRecipient();
                    }

                    return new ChatMessageDTO(
                            message.getSender(),
                            message.getRecipient(),
                            contentToShow,
                            message.getTimestamp()
                    );
                })
                .sorted(Comparator.comparing(ChatMessageDTO::getTimestamp))
                .toList();
    }

    public void saveMessage(ChatMessage message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(message.getSender());
        chatMessage.setRecipient(message.getRecipient());
        chatMessage.setEncryptedForRecipient(message.getEncryptedForRecipient());
        chatMessage.setEncryptedForSender(message.getEncryptedForSender());
        chatMessage.setContent(message.getContent());
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(chatMessage);
    }
}
