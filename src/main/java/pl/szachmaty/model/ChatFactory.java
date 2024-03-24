package pl.szachmaty.model;

import jakarta.transaction.Transactional;
import pl.szachmaty.model.dto.ChatMemberDto;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.model.repository.UserRepository;
import pl.szachmaty.model.value.UserId;

import java.util.Set;
import java.util.stream.Collectors;

public class ChatFactory {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    public ChatFactory(UserRepository userRepository, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    @Transactional
    public Chat create(Set<ChatMemberDto> chatMembers) {
        var users = chatMembers.stream()
                .map(this::findOrCreateUser)
                .collect(Collectors.toSet());

        var chat = new Chat();
        chat.setChatMembers(users);

        return chatRepository.save(chat);
    }

    private User findOrCreateUser(ChatMemberDto chatMember) {
        var maybeUser = userRepository.findUserByUserId(chatMember.getUserId());
        if (maybeUser != null) {
            return maybeUser;
        }

        var user = User.builder()
                .userId(new UserId(chatMember.getUserId()))
                .username(chatMember.getUsername())
                .build();

        return userRepository.save(user);
    }

}
