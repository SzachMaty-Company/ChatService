package pl.szachmaty.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.szachmaty.model.entity.Chat;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select ch from Chat ch left join fetch ch.chatMembers where ch.id = :chatId")
    Optional<Chat> findChatFetchMembers(Long chatId);

}