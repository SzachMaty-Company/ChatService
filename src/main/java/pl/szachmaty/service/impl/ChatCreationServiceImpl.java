package pl.szachmaty.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szachmaty.model.dto.ChatCreationRequest;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.model.repository.UserRepository;
import pl.szachmaty.service.ChatCreationService;

import java.util.Set;

@AllArgsConstructor
@Service
public class ChatCreationServiceImpl implements ChatCreationService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Override
    public Chat createChat(ChatCreationRequest dto) {
        var chat = new Chat();
        // TODO: in production ready app this should be done by requesting necessary data from user data service
//        var members = dto.getChatMembersIds().stream()
//                .map(userRepository::getReferenceById)
//                .collect(Collectors.toSet());

        chat.setChatMembers(Set.of());

        return chatRepository.save(chat);
    }

}
