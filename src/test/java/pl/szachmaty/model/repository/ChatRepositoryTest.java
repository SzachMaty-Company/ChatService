package pl.szachmaty.model.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.model.entity.User;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("dev")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChatRepositoryTest {

    @Autowired
    ChatRepository chatRepository;

    @Test
    void givenTwoChatMembers_whenPersistChat_thenSuccess() {
        // given
        final var member1 = User.builder()
                .name("bob")
                .surname("bob")
                .build();

        final var member2 = User.builder()
                .name("alice")
                .surname("alice")
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

}