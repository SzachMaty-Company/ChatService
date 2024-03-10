package pl.szachmaty.model.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.szachmaty.model.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Slice<Message> findMessagesByChatId(Long chatId, Pageable pageable);

}