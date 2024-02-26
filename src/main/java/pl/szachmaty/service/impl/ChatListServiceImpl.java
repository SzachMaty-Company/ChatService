package pl.szachmaty.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.model.value.UserId;
import pl.szachmaty.service.ChatListService;

@Service
@AllArgsConstructor
public class ChatListServiceImpl implements ChatListService {

    private final ChatRepository chatRepository;

    @Override
    public Slice<Chat> getUserChats(String userId, Pageable pageable) {
        return chatRepository.findUserChats(userId, pageable);
    }

}
