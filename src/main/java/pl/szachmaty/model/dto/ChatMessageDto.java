package pl.szachmaty.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ChatMessageDto {

    private Long chatId;
    private String senderId;
    private LocalDateTime timestamp;
    private String message;

}
