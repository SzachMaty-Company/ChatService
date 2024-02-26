package pl.szachmaty.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pl.szachmaty.model.dto.Message;
import pl.szachmaty.model.dto.ChatMessageDto;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.model.repository.MessageRepository;
import pl.szachmaty.model.repository.UserRepository;
import pl.szachmaty.security.ChatParticipantValidator;
import pl.szachmaty.service.MessageSendingService;

@Service
@AllArgsConstructor
public class MessageSendingServiceImpl implements MessageSendingService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRepository chatRepository;
    private final ModelMapper modelMapper;
    private final ChatParticipantValidator chatParticipantValidator;

    @Override
    @Transactional
    public void sendMessage(Message inboundMessage, User user) {
        boolean canSentMessageToChat = chatParticipantValidator.isUserChatParticipant(user, inboundMessage.getChatId());
        if (!canSentMessageToChat) {
            throw new AccessDeniedException("user is not participant of this chat, cannot send message");
        }

        var message = pl.szachmaty.model.entity.Message.builder()
                .message(inboundMessage.getMessage())
                .chat(chatRepository.getReferenceById(inboundMessage.getChatId()))
                .sender(userRepository.getReferenceById(user.getId()))
                .build();

        var savedMessage = messageRepository.save(message);
//        var messageDto = modelMapper.map(savedMessage, MessageResponseDto.class);
        var messageDto = modelMapper.map(message, ChatMessageDto.class);

        var chat = chatRepository.findChatFetchMembers(inboundMessage.getChatId()).orElseThrow();
        var members = chat.getChatMembers();

        for (var member : members) {
            simpMessagingTemplate.convertAndSendToUser(member.getUserId().getId(), "/queue/messages", messageDto);
        }
    }
}
