package pl.szachmaty.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pl.szachmaty.model.dto.ChatMessageDto;
import pl.szachmaty.model.dto.Message;
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
    public void sendMessage(Message messageToSend, User sender) {
        boolean canSentMessageInChat = chatParticipantValidator.isUserChatParticipant(sender, messageToSend.getChatId());
        if (!canSentMessageInChat) {
            throw new AccessDeniedException("User is not member of this chat, cannot send message");
        }

        var message = pl.szachmaty.model.entity.Message.builder()
                .message(messageToSend.getMessage())
                .chat(chatRepository.getReferenceById(messageToSend.getChatId()))
                .sender(userRepository.getReferenceById(sender.getId()))
                .build();

        var savedMessage = messageRepository.save(message);
        var messageDto = modelMapper.map(savedMessage, ChatMessageDto.class);

        var chat = chatRepository.findChatFetchMembers(savedMessage.getChat().getId()).orElseThrow();
        var members = chat.getChatMembers();

        for (var member : members) {
            simpMessagingTemplate.convertAndSendToUser(member.getUserId().getId(), "/queue/messages", messageDto);
        }
    }

}
