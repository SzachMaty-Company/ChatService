package pl.szachmaty.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pl.szachmaty.model.MessageFactory;
import pl.szachmaty.model.dto.ChatMessageDto;
import pl.szachmaty.model.dto.Message;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.model.repository.MessageRepository;
import pl.szachmaty.model.repository.UserRepository;
import pl.szachmaty.security.ChatParticipantValidator;
import pl.szachmaty.service.MessageSendingService;

@Service
public class MessageSendingServiceImpl implements MessageSendingService {

    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRepository chatRepository;
    private final ChatParticipantValidator chatParticipantValidator;
    private final MessageFactory messageFactory;

    public MessageSendingServiceImpl(MessageRepository messageRepository, UserRepository userRepository, SimpMessagingTemplate simpMessagingTemplate, ChatRepository chatRepository, ChatParticipantValidator chatParticipantValidator) {
        this.messageRepository = messageRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatRepository = chatRepository;
        this.chatParticipantValidator = chatParticipantValidator;
        this.messageFactory = new MessageFactory(chatRepository, userRepository);
    }

    @Override
    @Transactional
    public void sendMessage(Message messageToSend, User sender) {
        boolean canSentMessageInChat = chatParticipantValidator.isUserChatMember(sender, messageToSend.getChatId());
        if (!canSentMessageInChat) {
            throw new AccessDeniedException("User is not member of this chat, cannot send message");
        }

        var savedMessage = messageRepository.save(messageFactory.createMessage(messageToSend, sender));
        var messageDto = messageFactory.createDto(savedMessage);

        var chat = chatRepository.findChatFetchMembers(savedMessage.getChat().getId()).orElseThrow();

        for (var member : chat.getChatMembers()) {
            simpMessagingTemplate.convertAndSendToUser(member.getUserId().getId(), "/queue/messages", messageDto);
        }
    }

}
