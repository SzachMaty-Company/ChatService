package pl.szachmaty.service;

import org.springframework.data.domain.Pageable;
import pl.szachmaty.model.dto.MessageOutputDto;

import java.util.List;

public interface MessageQueryService {

    List<MessageOutputDto> findMessages(Long chatId, Pageable pageable);

}
