package pl.szachmaty.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
public class ChatListItemDto {

    private String message;
    private Long messageId;
    private Timestamp messageTimestamp;
    private String senderId;
    private String username;
    private Long chatId;
    private Set<ChatMemberDto> chatMembers;

}
