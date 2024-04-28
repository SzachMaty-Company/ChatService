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

    @Query(value = """
        select
            xxx.chat_id as chatId,
            xxx.max_timestamp as messageTimestamp,
            m.message as message,
            m.id as messageId,
            u.global_user_id as senderId,
            u.username as username
        from (
            select
                ch.id as chat_id,
                max(m.timestamp) as max_timestamp
            from chat_service.chat ch
            join chat_service.user_chat uch on uch.chat_id = ch.id
            join chat_service.user uu on uch.user_id = uu.id
            left join chat_service.message m on ch.id = m.chat_id
            where uu.global_user_id = :userId
            group by ch.id
        ) xxx
        left join chat_service.message m on xxx.max_timestamp = m.timestamp and
                                            xxx.chat_id = m.chat_id
        left join chat_service.user u on m.sender_id = u.id
        order by messageTimestamp desc
    """, nativeQuery = true)
    Slice<ChatListItem> getUserChats(String userId, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(ch) > 0 THEN true ELSE false END FROM Chat ch JOIN ch.chatMembers u WHERE u.id = :userId AND ch.id = :chatId")
    boolean existsChatWithUser(long userId, long chatId);

    @Query(value = """
                select uch1.chat_id
                from chat_service.user_chat uch1
                join chat_service.user u1 on u1.global_user_id = :inviteSenderId
                where uch1.user_id = u1.id
                intersect
                select uch2.chat_id
                from chat_service.user_chat uch2
                join chat_service.user u2 on u2.global_user_id = :inviteReceiverId
                where uch2.user_id = u2.id
    """, nativeQuery = true)
    Optional<Long> findChatByMembersIds(String inviteSenderId, String inviteReceiverId);

}