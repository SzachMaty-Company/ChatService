package pl.szachmaty.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import pl.szachmaty.model.dto.ChatCreationRequest;
import pl.szachmaty.model.dto.ChatCreationResponse;
import pl.szachmaty.model.dto.ChatListItemDto;
import pl.szachmaty.model.dto.Message;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.security.UserAuthenticationToken;
import pl.szachmaty.service.ChatCreationService;
import pl.szachmaty.service.ChatListService;
import pl.szachmaty.service.MessageSendingService;

import java.security.Principal;

@RestController
@CrossOrigin
@AllArgsConstructor
public class ChatController {

    final ChatListService chatListService;
    final ChatCreationService chatCreationService;
    final MessageSendingService messageSendingService;

    // TODO: require authorization
    @GetMapping(path = "/user/{userId}/chats")
    ResponseEntity<Slice<ChatListItemDto>> getChatListForUser(@PathVariable String userId,
                                                              Pageable pageable) {
        var chats = chatListService.getUserChats(userId, pageable);
        var chatsDto = chats.map(ChatListItemDto::convert);

        return ResponseEntity.ok(chatsDto);
    }

    @Profile({"dev", "local"})
    @MessageMapping("/message")
    void sendMessageInChat(Message message, Principal principal) {
        User user = (User) ((UserAuthenticationToken) principal).getPrincipal();
        messageSendingService.sendMessage(message, user);
    }

    @Profile({"dev", "local"})
    @PostMapping(path = "/chat")
    ResponseEntity<ChatCreationResponse> createChat(@RequestBody ChatCreationRequest chatCreationRequest) {
        var chat = chatCreationService.createChat(chatCreationRequest);
        var chatCreationResponse = new ChatCreationResponse(chat.getId());

        return ResponseEntity.ok(chatCreationResponse);
    }

}
