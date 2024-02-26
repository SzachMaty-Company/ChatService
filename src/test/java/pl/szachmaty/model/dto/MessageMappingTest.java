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

    @Test
    void givenChat_whenMapToDto_thenAllFieldsMatch() {
        // given
        User user1 = new User();
        user1.setId(10L);

        User user2 = new User();
        user2.setId(20L);

        Chat chat = new Chat();
        chat.setId(100L);
        chat.setChatMembers(Set.of(user1, user2));

        // when
        ChatListItemDto dto = modelMapper.map(chat, ChatListItemDto.class);

        // then
        Assertions.assertEquals(chat.getId(), dto.getId());
        Assertions.assertEquals(dto.getChatMembers().size(), 2);
        Assertions.assertTrue(dto.getChatMembers().contains(user1.getId()));
        Assertions.assertTrue(dto.getChatMembers().contains(user2.getId()));
    }

}
