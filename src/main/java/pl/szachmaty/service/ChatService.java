package pl.szachmaty.service;

import org.springframework.data.domain.Pageable;
import pl.szachmaty.model.entity.Chat;

import java.util.List;

public interface ChatService {

    List<Chat> getUserChats(Long userId, Pageable pageable);

}
