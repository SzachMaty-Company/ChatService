package pl.szachmaty.service;

import pl.szachmaty.model.dto.MessageRequestDto;

public interface MessageSendingService {

    void sendMessage(MessageRequestDto messageRequestDto, Long chatId, Long senderId);

}
