package pl.szachmaty.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    private Long chatId;
    private String messageType; // can be 'message', 'game_invite'
    private String message;

}
