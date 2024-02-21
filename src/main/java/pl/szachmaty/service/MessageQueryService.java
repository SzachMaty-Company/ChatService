package pl.szachmaty.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import pl.szachmaty.model.dto.MessageResponseDto;

public interface MessageQueryService {

    Slice<MessageResponseDto> findMessages(Long chatId, Pageable pageable);

}
