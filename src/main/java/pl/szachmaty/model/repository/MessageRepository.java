package pl.szachmaty.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szachmaty.model.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}