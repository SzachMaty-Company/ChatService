package pl.szachmaty;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest
class ChatListServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
