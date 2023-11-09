package pl.szachmaty.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szachmaty.model.Message;

@RestController
public class ChatController {

    //    FOR TESTING
    @GetMapping("/test")
    public String isPies() {
        return "Gratulacje. Dziala!";
    }
    @MessageMapping("/chat")
    public Message sendMessage(@Payload Message message) {
        return message;
    }
}