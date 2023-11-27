package pl.szachmaty.service.impl;

import org.springframework.stereotype.Service;
import pl.szachmaty.service.ChatCreationService;

import java.util.Set;

@Service
public class ChatCreationServiceImpl implements ChatCreationService {
    @Override
    public Long createChat(Set<Long> chatMembersId) {
        return null;
    }
}
