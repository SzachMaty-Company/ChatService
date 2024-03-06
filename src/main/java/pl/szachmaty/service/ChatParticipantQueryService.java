package pl.szachmaty.service;

import pl.szachmaty.model.dto.ChatParticipantDto;

import java.util.List;

public interface ChatParticipantQueryService {

    List<ChatParticipantDto> queryChatParticipants(long chatId);

}
