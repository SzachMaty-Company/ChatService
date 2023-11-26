package pl.szachmaty.model.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import pl.szachmaty.model.entity.User;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("dev")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void givenUser_whenPersist_thenSuccess() {
        // given
        var user = User.builder()
                .name("bob")
                .surname("bob")
                .build();

        // when
        var savedUser = userRepository.save(user);

        // then
        var fetchedUser = userRepository.findById(savedUser.getId())
                .orElseThrow();
        assertThat(fetchedUser).isNotNull();
        assertThat(fetchedUser.getName()).isEqualTo("bob");
        assertThat(fetchedUser.getSurname()).isEqualTo("bob");
    }

}