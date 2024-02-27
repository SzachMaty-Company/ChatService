package pl.szachmaty.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.szachmaty.model.value.UserId;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ChatMessageDto {

    private Long chatId;
    private UserId senderId;
    private LocalDateTime timestamp;
    private String message;

}
