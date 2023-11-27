package pl.szachmaty.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.service.ChatListService;

@Service
public class ChatListServiceImpl implements ChatListService {

    ChatRepository chatRepository;

    @Override
    public Slice<Chat> getUserChats(Long userId, Pageable pageable) {
        return chatRepository.findUserChats(userId, pageable);
    }
}
