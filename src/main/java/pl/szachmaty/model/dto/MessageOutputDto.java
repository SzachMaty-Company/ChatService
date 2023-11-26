package pl.szachmaty.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageOutputDto {

    private Long chatId;
    private Long senderId;
    private Long receiverId;
    private LocalDateTime timestamp;
    private String message;

}
