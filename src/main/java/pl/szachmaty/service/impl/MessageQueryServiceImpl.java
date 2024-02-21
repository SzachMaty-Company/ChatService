package pl.szachmaty.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import pl.szachmaty.model.dto.MessageResponseDto;
import pl.szachmaty.model.repository.MessageRepository;
import pl.szachmaty.service.MessageQueryService;

@Service
@AllArgsConstructor
public class MessageQueryServiceImpl implements MessageQueryService {

    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Slice<MessageResponseDto> findMessages(Long chatId, Pageable pageable) {
        return messageRepository.findMessagesByChatIdOrderByTimestamp(chatId, pageable)
                .map(m -> modelMapper.map(m, MessageResponseDto.class));
    }
}
