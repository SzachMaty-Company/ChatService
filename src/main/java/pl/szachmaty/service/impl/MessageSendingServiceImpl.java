package pl.szachmaty.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.szachmaty.model.dto.MessageRequestDto;
import pl.szachmaty.model.dto.MessageResponseDto;
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
    private final ModelMapper modelMapper;

    @Override
    public void sendMessage(MessageRequestDto messageRequestDto, Long chatId, Long senderId) {
        var message = new Message();
        message.setMessage(messageRequestDto.getMessage());
        message.setSender(userRepository.getReferenceById(senderId));
        message.setChat(chatRepository.getReferenceById(chatId));

        var savedMessage = messageRepository.save(message);
        var messageDto = modelMapper.map(savedMessage, MessageResponseDto.class);

        var chat = chatRepository.findChatFetchMembers(chatId).orElseThrow();
        var members = chat.getChatMembers();

        for (var member : members) {
            simpMessagingTemplate.convertAndSendToUser(String.valueOf(member.getId()), "/messages", messageDto);
        }
    }
}
