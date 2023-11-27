package pl.szachmaty.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.model.repository.UserRepository;
import pl.szachmaty.service.ChatCreationService;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ChatCreationServiceImpl implements ChatCreationService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Override
    public Long createChat(Set<Long> chatMembersIds) {
        var chat = new Chat();
        var members = chatMembersIds.stream()
                .map(userRepository::getReferenceById)
                .collect(Collectors.toSet());

        chat.setChatMembers(members);

        var savedChat = chatRepository.save(chat);
        return savedChat.getId();
    }

}
