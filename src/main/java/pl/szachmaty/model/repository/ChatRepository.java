package pl.szachmaty.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szachmaty.model.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}