package pl.szachmaty.model.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import pl.szachmaty.model.entity.Message;
import pl.szachmaty.model.entity.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("dev")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MessageRepositoryTest {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @Sql("/model/repository/chat/message.sql")
    void givenMessage_whenPersist_thenSuccess() {
        // given
        final var bobId = 500L;
        final var aliceId = 501L;

        var bob = userRepository.getReferenceById(bobId);
        var alice = userRepository.getReferenceById(aliceId);

        var messageToBob = Message.builder()
                .message("hi, alice")
                .sender(bob)
                .build();

        var messageToAlice = Message.builder()
                .message("hi, bob")
                .sender(alice)
                .build();

        // when
        var savedMessageToBob = messageRepository.save(messageToBob);
        var savedMessageToAlice = messageRepository.save(messageToAlice);

        // then
        var fetchedMessageToBob = messageRepository.findById(savedMessageToBob.getId())
                .orElseThrow();
        var fetchedMessageToAlice = messageRepository.findById(savedMessageToAlice.getId())
                .orElseThrow();

        assertThat(fetchedMessageToBob).isNotNull();
        assertThat(fetchedMessageToAlice).isNotNull();
        assertThat(fetchedMessageToBob.getMessage()).isNotNull();
        assertThat(fetchedMessageToAlice.getMessage()).isNotNull();
        assertThat(fetchedMessageToBob.getSender().getId()).isEqualTo(bobId);
        assertThat(fetchedMessageToAlice.getSender().getId()).isEqualTo(aliceId);
    }

}