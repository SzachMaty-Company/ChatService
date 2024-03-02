package pl.szachmaty.model.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.model.value.UserId;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void givenUser_whenPersist_thenSuccess() {
        // given
        var user = User.builder()
                .username("bob")
                .userId(new UserId("1000"))
                .build();

        // when
        var savedUser = userRepository.save(user);

        // then
        var fetchedUser = userRepository.findById(savedUser.getId())
                .orElseThrow();
        assertThat(fetchedUser).isNotNull();
        assertThat(fetchedUser.getUsername()).isEqualTo("bob");
    }

}