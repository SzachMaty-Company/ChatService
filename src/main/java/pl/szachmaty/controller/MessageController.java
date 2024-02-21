package pl.szachmaty.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.szachmaty.model.dto.MessageResponseDto;
import pl.szachmaty.service.MessageQueryService;

@RestController
@AllArgsConstructor
@CrossOrigin
public class MessageController {

    MessageQueryService messageQueryService;

    @GetMapping(path = "/chat/{chatId}/messages")
    ResponseEntity<Slice<MessageResponseDto>> queryMessages(@PathVariable Long chatId, Pageable pageable) {
        var messages = messageQueryService.findMessages(chatId, pageable);
        return ResponseEntity.ok(messages);
    }

}
