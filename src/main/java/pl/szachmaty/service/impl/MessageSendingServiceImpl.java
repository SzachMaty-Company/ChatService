package pl.szachmaty.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szachmaty.model.dto.MessageInputDto;
import pl.szachmaty.model.entity.Message;
import pl.szachmaty.model.repository.MessageRepository;
import pl.szachmaty.model.repository.UserRepository;
import pl.szachmaty.service.MessageSendingService;

@Service
@AllArgsConstructor
public class MessageSendingServiceImpl implements MessageSendingService {

    MessageRepository messageRepository;
    UserRepository userRepository;

    @Override
    public void sendMessage(MessageInputDto messageInputDto, Long chatId, Long senderId) {
        var message = new Message();
        message.setMessage(messageInputDto.getMessage());
        message.setSender(userRepository.getReferenceById(senderId));

        var savedMessage = messageRepository.save(message);
        // send message to broker
        // ...
    }
}
