package org.example.militarysystem.chat;

import org.example.militarysystem.chat.dto.ChatDTO;
import org.example.militarysystem.chat.dto.ChatMessageDTO;
import org.example.militarysystem.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/chat")
@RestController()
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/availableUsers")
    public ResponseEntity<List<String>> getAvailable() {
        List<User> usersWithoutChatWith = chatService.getUsersWithoutChatWith();
        return ResponseEntity.ok(usersWithoutChatWith.stream().map(User::getUsername).toList());
    }

    @GetMapping("/getChatsForCurrentUser")
    public ResponseEntity<List<ChatDTO>> getAllChatsOfCurrentUser() {
        List<ChatDTO> chats = chatService.getAllChatsOfCurrentUser()
                .stream()
                .map(chat -> new ChatDTO(
                        chat.getId(),
                        chat.getName(),
                        chat.getParticipants().stream().map(User::getUsername).toList(),
                        null
                ))
                .toList();
        return ResponseEntity.ok(chats);
    }

    @DeleteMapping("/deleteChat/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        chatService.deleteChat(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/createChat")
    public ResponseEntity<Void> createChat(@RequestBody ChatDTO chatDTO) {
        chatService.saveChat(chatDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getMessages/{recipient}")
    public ResponseEntity<List<ChatMessageDTO>> getMessages(@PathVariable String recipient) {
        List<ChatMessageDTO> messages = chatService.getMessagesWithRecipient(recipient);
        return ResponseEntity.ok(messages);
    }
}
