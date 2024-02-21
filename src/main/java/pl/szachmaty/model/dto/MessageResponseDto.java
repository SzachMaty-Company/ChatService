package pl.szachmaty.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MessageResponseDto {

    private Long chatId;
    private Long senderId;
    private LocalDateTime timestamp;
    private String message;

}
