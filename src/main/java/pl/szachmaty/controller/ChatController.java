package pl.szachmaty.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import pl.szachmaty.model.Message;
import pl.szachmaty.model.dto.ChatOutputDto;
import pl.szachmaty.model.dto.MessageInputDto;
import pl.szachmaty.service.ChatService;
import pl.szachmaty.service.MessageSendingService;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class ChatController {

    ChatService chatService;
    MessageSendingService messageSendingService;

    @MessageMapping("/chat")
    Message sendMessage(@Payload Message message) {
        return message;
    }

    @GetMapping(path = "/{userId}/chats")
    ResponseEntity<List<ChatOutputDto>> getChatsForUser(@PathVariable Long userId) {
        var chats = chatService.getUserChats(userId, Pageable.unpaged());
        var chatsDto = chats.stream()
                .map(ChatOutputDto::convert)
                .toList();

        return ResponseEntity.ok(chatsDto);
    }

//    for testing
    @Profile("dev")
    @PostMapping(path = "/chat/{chatId}/sender/{senderId}")
    ResponseEntity<Void> sendMessageInChat(@RequestBody MessageInputDto messageInputDto,
                                           @PathVariable Long chatId,
                                           @PathVariable Long senderId) {
        messageSendingService.sendMessage(messageInputDto, chatId, senderId);
        return ResponseEntity.noContent().build();
    }

}
