package pl.szachmaty.model.dto;

import java.sql.Timestamp;

public interface ChatListItem {

    String getMessage();
    Long getMessageId();
    Timestamp getMessageTimestamp();
    String getSenderId();
    String getUsername();
    Long getChatId();

}
