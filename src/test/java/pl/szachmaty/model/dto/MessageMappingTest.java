package pl.szachmaty.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pl.szachmaty.config.ModelMapperConfig;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.model.entity.Message;
import pl.szachmaty.model.entity.User;

import java.time.LocalDateTime;
import java.util.Set;

@SpringBootTest
@ContextConfiguration(classes = {ModelMapperConfig.class})
public class MessageMappingTest {

    @Autowired
    ModelMapper modelMapper;

    @Test
    void givenMessage_whenMapToDto_thenAllFieldsMatch() {
        // given
        Chat chat = new Chat();
        chat.setId(100L);

        User user = new User();
        user.setId(10L);

        Message message = new Message();
        message.setMessage("Hello");
        message.setSender(user);
        message.setTimestamp(LocalDateTime.now());
        message.setChat(chat);

        // when
        ChatMessageDto dto = modelMapper.map(message, ChatMessageDto.class);

        // then
        Assertions.assertEquals(dto.getMessage(), message.getMessage());
        Assertions.assertEquals(dto.getChatId(), message.getChat().getId());
        Assertions.assertEquals(dto.getSenderId(), message.getSender().getId());
        Assertions.assertEquals(dto.getTimestamp(), message.getTimestamp());
    }

}
