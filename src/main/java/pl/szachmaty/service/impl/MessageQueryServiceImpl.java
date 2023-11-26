package pl.szachmaty.service.impl;

import org.springframework.data.domain.Pageable;
import pl.szachmaty.model.dto.MessageOutputDto;
import pl.szachmaty.service.MessageQueryService;

import java.util.List;

public class MessageQueryServiceImpl implements MessageQueryService {
    @Override
    public List<MessageOutputDto> findMessages(Long chatId, Pageable pageable) {
        return null;
    }
}
