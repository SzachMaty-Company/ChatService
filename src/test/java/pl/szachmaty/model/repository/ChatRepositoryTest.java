package pl.szachmaty.model.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.model.value.UserId;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChatRepositoryTest {

    @Autowired
    ChatRepository chatRepository;

    @Test
    void givenTwoChatMembers_whenPersistChat_thenSuccess() {
        // given
        final var member1 = User.builder()
                .userId(new UserId("100"))
                .username("alice")
                .build();

        final var member2 = User.builder()
                .userId(new UserId("101"))
                .username("alice")
                .build();

        // when
        final var chat = new Chat();
        chat.setChatMembers(Set.of(member1, member2));
        var savedChat = chatRepository.save(chat);

        // then
        var fetchedChat = chatRepository.findById(savedChat.getId())
                .orElseThrow();

        assertThat(fetchedChat).isNotNull();
        assertThat(fetchedChat.getChatMembers()).isNotNull();
        assertThat(fetchedChat.getChatMembers()).hasSize(2);
    }

    @Test
    @Sql("/sql/chat/chat-list.sql")
    void givenChatMemberId_whenFindUserChats_thenReturnUsersChatList() {
        // given
        final UserId userId = new UserId("global-id-11");

        // when
        var chats = chatRepository.getUserChats(userId.getId(), Pageable.ofSize(1));

        // then
        assertThat(chats).isNotNull();
        assertThat(chats.getContent().size()).isEqualTo(1);
        assertThat(chats.getContent().get(0).getMessage()).isNotNull();
        assertThat(chats.getContent().get(0).getUsername()).isNotNull();
        assertThat(chats.getContent().get(0).getChatId()).isNotNull();
    }

}