package pl.szachmaty.model.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.szachmaty.model.dto.ChatListItem;
import pl.szachmaty.model.entity.Chat;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT ch FROM Chat ch LEFT JOIN FETCH ch.chatMembers WHERE ch.id = :chatId")
    Optional<Chat> findChatFetchMembers(Long chatId);

    @Query("SELECT u.chats FROM User u WHERE u.userId.id = :userId")
    Slice<Chat> findUserChats(String userId, Pageable pageable);

//    List<?> findUserChatsWithLastMessageInChat(String userId);

    @Query(value = """
                select m.message as message,
                       m.id as messageId,
                       m."timestamp" as messageTimestamp,
                       u.global_user_id as senderId,
                       u.username as username,
                       uch.chat_id as chatId
                from (select mm.chat_id,
                             max(mm."timestamp") as last_message_timestamp
                      from chat_service."message" mm
                      group by mm.chat_id
                      order by max(mm."timestamp")
                      ) sq
                         join chat_service."message" m on m."timestamp" = sq.last_message_timestamp
                         join chat_service.user_chat uch on uch.chat_id = sq.chat_id
                         join chat_service."user" u on uch.user_id = u.id
                where u.global_user_id = :userId
            """, nativeQuery = true)
    Slice<ChatListItem> getUserChats(String userId, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(ch) > 0 THEN true ELSE false END FROM Chat ch JOIN ch.chatMembers u WHERE u.id = :userId AND ch.id = :chatId")
    boolean existsChatWithUser(long userId, long chatId);

}