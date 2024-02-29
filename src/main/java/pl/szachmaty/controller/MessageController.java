package pl.szachmaty.controller;

import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.szachmaty.model.dto.ChatMessageDto;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.service.MessageQueryService;

import java.security.Principal;

@RestController
@AllArgsConstructor
@CrossOrigin
public class MessageController {

    final MessageQueryService messageQueryService;

    @GetMapping(path = "/chat/{chatId}/messages")
    @PreAuthorize(
            "@chatParticipantValidator.isUserChatMember(principal, #chatId)"
    )
    ResponseEntity<Slice<ChatMessageDto>> queryMessages(@PathVariable Long chatId, @ParameterObject Pageable pageable) {
        var correctedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.unsorted());
        var messages = messageQueryService.queryMessages(chatId, correctedPageable);
        return ResponseEntity.ok(messages);
    }

}
