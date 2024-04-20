package pl.szachmaty.model;

import lombok.extern.slf4j.Slf4j;
import pl.szachmaty.model.dto.GameInviteDto;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.model.entity.Message;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.model.repository.UserRepository;

@Slf4j
public class InviteFactory {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public InviteFactory(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public Message createInvite(GameInviteDto gameInviteDto) {
        Long chatId = chatRepository.findChatByMembersIds(gameInviteDto.getInviteSenderId(), gameInviteDto.getInviteReceiverId())
                .orElseThrow(() -> new RuntimeException("cannot find chat with participants: " + gameInviteDto.getInviteSenderId() + " " + gameInviteDto.getInviteReceiverId()));

        log.info("found common chat: " + chatId);

//        Chat chat = chatRepository.findChatByMembersIds(gameInviteDto.getInviteSenderId(), gameInviteDto.getInviteReceiverId())
//                .orElseThrow(() -> new RuntimeException("cannot find chat with participants: " + gameInviteDto.getInviteSenderId() + " " + gameInviteDto.getInviteReceiverId()));

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("JA PIERDOLE"));

        return Message.builder()
                .type("INVITE")
                .message(gameInviteDto.getGameCode())
                .chat(chat)
                .sender(userRepository.findUserByUserId(gameInviteDto.getInviteSenderId()))
                .build();
    }

}
