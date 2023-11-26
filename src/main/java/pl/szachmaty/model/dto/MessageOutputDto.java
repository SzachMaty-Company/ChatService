package pl.szachmaty.model.dto;

import lombok.Getter;
import lombok.Setter;
import pl.szachmaty.model.entity.Message;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageOutputDto {

    private Long chatId;
    private Long senderId;
    private LocalDateTime timestamp;
    private String message;

    public static MessageOutputDto convert(Message message) {
        var dto = new MessageOutputDto();
        dto.setChatId(message.getChat().getId());
        dto.setMessage(message.getMessage());
        dto.setSenderId(message.getSender().getId());
        dto.setTimestamp(message.getTimestamp());

        return dto;
    }

}
