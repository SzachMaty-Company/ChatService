package pl.szachmaty.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import pl.szachmaty.model.entity.Chat;

public interface ChatListService {

    Slice<Chat> getUserChats(Long userId, Pageable pageable);

}
