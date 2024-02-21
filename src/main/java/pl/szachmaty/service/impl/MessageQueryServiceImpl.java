package pl.szachmaty.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import pl.szachmaty.model.dto.MessageOutputDto;
import pl.szachmaty.model.repository.MessageRepository;
import pl.szachmaty.service.MessageQueryService;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageQueryServiceImpl implements MessageQueryService {

    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Slice<MessageOutputDto> findMessages(Long chatId, Pageable pageable) {
        return messageRepository.findMessagesByChatIdOrderByTimestamp(chatId, pageable)
                .map(m -> modelMapper.map(m, MessageOutputDto.class));
    }
}
