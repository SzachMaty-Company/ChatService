package pl.szachmaty.service.impl;

import org.springframework.stereotype.Service;
import pl.szachmaty.model.ChatFactory;
import pl.szachmaty.model.dto.ChatMemberDto;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.model.repository.UserRepository;
import pl.szachmaty.service.ChatCreationService;

import java.util.Set;

@Service
public class ChatCreationServiceImpl implements ChatCreationService {

    private final ChatFactory chatFactory;

    public ChatCreationServiceImpl(UserRepository userRepository, ChatRepository chatRepository) {
        this.chatFactory = new ChatFactory(userRepository, chatRepository);
    }

    @Override
    public Chat createChat(Set<ChatMemberDto> chatMembers) {
        return chatFactory.create(chatMembers);
    }

}
