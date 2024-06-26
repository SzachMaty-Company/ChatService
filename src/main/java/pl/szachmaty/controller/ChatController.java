package pl.szachmaty.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.results.graph.embeddable.internal.AggregateEmbeddableFetchImpl;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.szachmaty.model.dto.*;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.security.annotation.StompAuthenticationPrincipal;
import pl.szachmaty.service.ChatCreationService;
import pl.szachmaty.service.ChatListService;
import pl.szachmaty.service.ChatParticipantQueryService;
import pl.szachmaty.service.MessageSendingService;

import java.lang.annotation.Repeatable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
//@CrossOrigin(allowedHeaders = "*", originPatterns = "*")
@AllArgsConstructor
public class ChatController {

    final ChatListService chatListService;
    final ChatCreationService chatCreationService;
    final ChatParticipantQueryService chatParticipantService;
    final MessageSendingService messageSendingService;
    final ChatRepository chatRepository;

    @GetMapping(path = "/user/chats")
    ResponseEntity<Slice<ChatListItem>> getChatListForUser(@AuthenticationPrincipal User user, @ParameterObject Pageable pageable) {
        var correctedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.unsorted());
        return ResponseEntity.ok(chatListService.getUserChats(user.getUserId().getId(), correctedPageable));
    }

    @PreAuthorize(
            "@chatParticipantValidator.isUserChatMember(principal, #chatId)"
    )
    @GetMapping(path = "/chat/{chatId}/participants")
    ResponseEntity<List<ChatParticipantDto>> getChatParticipants(@PathVariable Long chatId) {
        return ResponseEntity.ok(chatParticipantService.queryChatParticipants(chatId));
    }

    @MessageMapping("/message")
    void sendMessageInChat(Message message, @StompAuthenticationPrincipal User user) {
        messageSendingService.sendMessage(message, user);
    }

    @Operation(description = "for internal purposes only, don't use it")
    @PostMapping(path = "/internal/chat")
    ResponseEntity<ChatCreationResponse> createChat(@RequestBody ChatCreationRequest chatCreationRequest) {
        log.info("friend add input dto: " + chatCreationRequest.toString());
        try {
            var chat = chatCreationService.createChat(chatCreationRequest.getChatMembers());
            var chatCreationResponse = new ChatCreationResponse(chat.getId());

            return ResponseEntity.ok(chatCreationResponse);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(description = "for internal purposes only, don't use it")
    @PostMapping(path = "/internal/game/invite")
    ResponseEntity<?> sendInviteInChat(@RequestBody GameInviteDto gameInviteDto) {
        log.info("invite send input dto: " + gameInviteDto.toString());
        try {
            messageSendingService.sendGameInvite(gameInviteDto);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
