package pl.szachmaty.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import pl.szachmaty.model.dto.ChatMessageDto;

public interface MessageQueryService {

    Slice<ChatMessageDto> queryMessages(Long chatId, Pageable pageable);

}
