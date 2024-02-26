package pl.szachmaty.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.model.value.UserId;

public interface ChatListService {

    Slice<Chat> getUserChats(String userId, Pageable pageable);

}
