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
import pl.szachmaty.model.dto.ChatCreationRequestDto;
import pl.szachmaty.model.dto.ChatCreationResponseDto;
import pl.szachmaty.model.dto.ChatResponseDto;
import pl.szachmaty.model.dto.MessageRequestDto;
import pl.szachmaty.service.ChatCreationService;
import pl.szachmaty.service.ChatListService;
import pl.szachmaty.service.MessageSendingService;

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
    ResponseEntity<Slice<ChatResponseDto>> getChatsForUser(@PathVariable Long userId,
                                                           Pageable pageable) {
        var chats = chatListService.getUserChats(userId, pageable);
        var chatsDto = chats.map(ChatResponseDto::convert);

        return ResponseEntity.ok(chatsDto);
    }

    @Profile({"dev", "local"})
    @PostMapping(path = "/chat/{chatId}/sender/{senderId}")
    ResponseEntity<Void> sendMessageInChat(@RequestBody MessageRequestDto messageRequestDto,
                                           @PathVariable Long chatId,
                                           @PathVariable Long senderId) {
        messageSendingService.sendMessage(messageRequestDto, chatId, senderId);
        return ResponseEntity.noContent().build();
    }

    @Profile({"dev", "local"})
    @PostMapping(path = "/chat")
    ResponseEntity<ChatCreationResponseDto> createChat(@RequestBody ChatCreationRequestDto chatCreationRequestDto) {
        var chatId = chatCreationService.createChat(chatCreationRequestDto.getChatMembersIds());
        var chatCreationOutputDto = new ChatCreationResponseDto();
        chatCreationOutputDto.setChatId(chatId);

        return ResponseEntity.ok(chatCreationOutputDto);
    }

}
