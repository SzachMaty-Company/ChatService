package pl.szachmaty.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import pl.szachmaty.model.Message;
import pl.szachmaty.model.dto.ChatCreationInputDto;
import pl.szachmaty.model.dto.ChatCreationOutputDto;
import pl.szachmaty.model.dto.ChatOutputDto;
import pl.szachmaty.model.dto.MessageInputDto;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.service.ChatCreationService;
import pl.szachmaty.service.ChatListService;
import pl.szachmaty.service.MessageSendingService;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class ChatController {

    private final ChatListService chatListService;
    private final ChatCreationService chatCreationService;
    private final MessageSendingService messageSendingService;

    @MessageMapping("/chat")
    Message sendMessage(@Payload Message message) {
        return message;
    }

    @GetMapping(path = "/user/{userId}/chats")
    ResponseEntity<Slice<ChatOutputDto>> getChatsForUser(@PathVariable Long userId,
                                                         Pageable pageable) {
        var chats = chatListService.getUserChats(userId, pageable);
        var chatsDto = chats.map(ChatOutputDto::convert);

        return ResponseEntity.ok(chatsDto);
    }

    @Profile("dev")
    @PostMapping(path = "/chat/{chatId}/sender/{senderId}")
    ResponseEntity<Void> sendMessageInChat(@RequestBody MessageInputDto messageInputDto,
                                           @PathVariable Long chatId,
                                           @PathVariable Long senderId) {
        messageSendingService.sendMessage(messageInputDto, chatId, senderId);
        return ResponseEntity.noContent().build();
    }

    @Profile("dev")
    @PostMapping(path = "/chat")
    ResponseEntity<ChatCreationOutputDto> createChat(@RequestBody ChatCreationInputDto chatCreationInputDto) {
        var chatId = chatCreationService.createChat(chatCreationInputDto.getChatMembersIds());
        var chatCreationOutputDto = new ChatCreationOutputDto();
        chatCreationOutputDto.setChatId(chatId);

        return ResponseEntity.ok(chatCreationOutputDto);
    }

}
