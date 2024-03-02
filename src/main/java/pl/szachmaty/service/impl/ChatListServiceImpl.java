package pl.szachmaty.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import pl.szachmaty.model.dto.ChatListItem;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.service.ChatListService;

@Service
@AllArgsConstructor
public class ChatListServiceImpl implements ChatListService {

    private final ChatRepository chatRepository;

    @Override
    public Slice<ChatListItem> getUserChats(String userId, Pageable pageable) {
        return chatRepository.getUserChats(userId, pageable);
    }

}
