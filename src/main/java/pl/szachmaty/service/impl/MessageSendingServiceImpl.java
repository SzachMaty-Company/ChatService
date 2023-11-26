package pl.szachmaty.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.szachmaty.model.dto.MessageInputDto;
import pl.szachmaty.model.dto.MessageOutputDto;
import pl.szachmaty.model.entity.Message;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.model.repository.MessageRepository;
import pl.szachmaty.model.repository.UserRepository;
import pl.szachmaty.service.MessageSendingService;

@Service
@AllArgsConstructor
public class MessageSendingServiceImpl implements MessageSendingService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRepository chatRepository;

    @Override
    public void sendMessage(MessageInputDto messageInputDto, Long chatId, Long senderId) {
        var message = new Message();
        message.setMessage(messageInputDto.getMessage());
        message.setSender(userRepository.getReferenceById(senderId));
        message.setChat(chatRepository.getReferenceById(chatId));

        var savedMessage = messageRepository.save(message);
        var messageDto = MessageOutputDto.convert(savedMessage);

        var chat = chatRepository.findChatFetchMembers(chatId).orElseThrow();
        var members = chat.getChatMembers();

        for (var member : members) {
            simpMessagingTemplate.convertAndSendToUser(String.valueOf(member.getId()), "/messages", messageDto);
        }
    }
}
