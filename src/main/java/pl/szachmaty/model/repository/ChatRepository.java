package pl.szachmaty.model.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.model.value.UserId;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select ch from Chat ch left join fetch ch.chatMembers where ch.id = :chatId")
    Optional<Chat> findChatFetchMembers(Long chatId);

    @Query("select u.chats from User u where u.userId.id = :userId")
    Slice<Chat> findUserChats(String userId, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(ch) > 0 THEN true ELSE false END FROM Chat ch JOIN ch.chatMembers u WHERE u.id = :userId AND ch.id = :chatId")
    boolean existsChatWithUser(long userId, long chatId);

}