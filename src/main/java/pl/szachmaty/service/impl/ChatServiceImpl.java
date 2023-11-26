package pl.szachmaty.service.impl;

import org.springframework.data.domain.Pageable;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.service.ChatService;

import java.util.List;

public class ChatServiceImpl implements ChatService {
    @Override
    public List<Chat> getUserChats(Long userId, Pageable pageable) {
        return null;
    }
}
