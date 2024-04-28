package pl.szachmaty.model.dto;

import java.sql.Timestamp;
import java.util.Set;

public interface ChatListItem {

    String getMessage();
    Long getMessageId();
    Timestamp getMessageTimestamp();
    String getSenderId();
    String getUsername();
    Long getChatId();

}
