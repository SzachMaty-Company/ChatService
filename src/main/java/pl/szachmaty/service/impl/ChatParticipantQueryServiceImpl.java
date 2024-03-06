package pl.szachmaty.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.szachmaty.model.dto.ChatParticipantDto;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.service.ChatParticipantQueryService;

import java.util.List;
import java.util.Set;

@Service
public class ChatParticipantQueryServiceImpl implements ChatParticipantQueryService {

    private final ChatRepository chatRepository;

    public ChatParticipantQueryServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public List<ChatParticipantDto> queryChatParticipants(long chatId) {
        return chatRepository.findById(chatId)
                .map(chat -> chat
                .getChatMembers()
                .stream()
                .map(ChatParticipantDto::fromUser)
                .toList()).orElseGet(List::of);
    }

}
