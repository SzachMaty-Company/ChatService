package pl.szachmaty.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import pl.szachmaty.model.dto.MessageOutputDto;

import java.util.List;

public interface MessageQueryService {

    Slice<MessageOutputDto> findMessages(Long chatId, Pageable pageable);

}
