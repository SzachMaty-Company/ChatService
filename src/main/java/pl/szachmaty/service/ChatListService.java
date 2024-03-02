package pl.szachmaty.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import pl.szachmaty.model.dto.ChatListItem;

public interface ChatListService {

    Slice<ChatListItem> getUserChats(String userId, Pageable pageable);

}
