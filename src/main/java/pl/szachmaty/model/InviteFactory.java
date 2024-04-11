package pl.szachmaty.model;

import pl.szachmaty.model.dto.GameInviteDto;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.model.entity.Message;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.model.repository.UserRepository;

public class InviteFactory {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public InviteFactory(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public Message createInvite(GameInviteDto gameInviteDto) {
        Chat chat = chatRepository.findChatByMembersIds(gameInviteDto.getInviteSenderId(), gameInviteDto.getInviteReceiverId())
                .orElseThrow(() -> new RuntimeException("cannot find chat with participants: " + gameInviteDto.getInviteSenderId() + " " + gameInviteDto.getInviteReceiverId()));

        return Message.builder()
                .type("INVITE")
                .message(gameInviteDto.getGameCode())
                .chat(chat)
                .sender(userRepository.findUserByUserId(gameInviteDto.getInviteSenderId()))
                .build();
    }

}
