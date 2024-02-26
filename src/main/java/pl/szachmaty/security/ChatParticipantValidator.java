package pl.szachmaty.security;

import org.springframework.stereotype.Component;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.model.repository.ChatRepository;

@Component
public class ChatParticipantValidator {

    private final ChatRepository chatRepository;

    public ChatParticipantValidator(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public boolean isUserChatParticipant(User user, Long chatId) {
        return chatRepository.existsChatWithUser(user.getId(), chatId);
    }

}
