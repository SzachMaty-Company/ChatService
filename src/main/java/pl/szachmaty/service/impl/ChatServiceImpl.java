package pl.szachmaty.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.service.ChatService;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Override
    public List<Chat> getUserChats(Long userId, Pageable pageable) {
        return null;
    }
}
