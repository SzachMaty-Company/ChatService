package pl.szachmaty.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.szachmaty.model.dto.ChatCreationRequest;
import pl.szachmaty.model.dto.ChatCreationResponse;
import pl.szachmaty.model.dto.ChatListItem;
import pl.szachmaty.model.dto.Message;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.security.annotation.StompAuthenticationPrincipal;
import pl.szachmaty.service.ChatCreationService;
import pl.szachmaty.service.ChatListService;
import pl.szachmaty.service.MessageSendingService;

@RestController
@CrossOrigin
@AllArgsConstructor
public class ChatController {

    final ChatListService chatListService;
    final ChatCreationService chatCreationService;
    final MessageSendingService messageSendingService;
    final ChatRepository chatRepository;

    @GetMapping(path = "/user/chats")
    ResponseEntity<Slice<ChatListItem>> getChatListForUser(@AuthenticationPrincipal User user, @ParameterObject Pageable pageable) {
        var correctedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.unsorted());
        return ResponseEntity.ok(chatListService.getUserChats(user.getUserId().getId(), correctedPageable));
    }

    @MessageMapping("/message")
    void sendMessageInChat(Message message, @StompAuthenticationPrincipal User user) {
        messageSendingService.sendMessage(message, user);
    }

    @Profile({"dev", "local"})
    @Operation(description = "for internal purposes only, don't use it")
    @PostMapping(path = "/chat")
    ResponseEntity<ChatCreationResponse> createChat(@RequestBody ChatCreationRequest chatCreationRequest) {
        var chat = chatCreationService.createChat(chatCreationRequest);
        var chatCreationResponse = new ChatCreationResponse(chat.getId());

        return ResponseEntity.ok(chatCreationResponse);
    }

}
