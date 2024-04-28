package pl.szachmaty.model;

import org.springframework.web.util.HtmlUtils;
import pl.szachmaty.model.dto.ChatMessageDto;
import pl.szachmaty.model.entity.Message;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.model.repository.UserRepository;

public class MessageFactory {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public MessageFactory(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public Message createMessage(pl.szachmaty.model.dto.Message message, User sender) {
        return Message.builder()
                .message(message.getMessage())
                .chat(chatRepository.getReferenceById(message.getChatId()))
                .type("MESSAGE")
                .sender(userRepository.getReferenceById(sender.getId()))
                .build();
    }

    public ChatMessageDto createDto(Message message) {
        ChatMessageDto dto = new ChatMessageDto();
        dto.setType(message.getType() == null ? "MESSAGE" : message.getType());
        dto.setMessage(HtmlUtils.htmlEscape(message.getMessage()));
        dto.setTimestamp(message.getTimestamp());
        dto.setChatId(message.getChat().getId());
        dto.setSenderId(message.getSender().getUserId().getId());

        return dto;
    }

}
