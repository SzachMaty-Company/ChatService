package pl.szachmaty.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.szachmaty.model.Message;

@RestController
@CrossOrigin
public class ChatController {

    @MessageMapping("/chat")
    public Message sendMessage(@Payload Message message) {
        return message;
    }
}
