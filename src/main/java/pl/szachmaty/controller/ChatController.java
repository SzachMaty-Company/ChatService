package pl.szachmaty.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.szachmaty.model.dto.*;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.security.UserJwtAuthenticationToken;
import pl.szachmaty.security.annotation.StompAuthenticationPrincipal;
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
    final ChatRepository chatRepository;

    @GetMapping(path = "/user/chats")
    ResponseEntity<Slice<ChatListItem>> getChatListForUser(@AuthenticationPrincipal User user, Pageable pageable) {
        return ResponseEntity.ok(chatListService.getUserChats(user.getUserId().getId(), pageable));
    }

    @MessageMapping("/message")
    void sendMessageInChat(Message message, @StompAuthenticationPrincipal User user) {
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
