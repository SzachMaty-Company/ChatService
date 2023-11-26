package pl.szachmaty.service;

import pl.szachmaty.model.dto.MessageInputDto;

public interface MessageSendingService {

    void sendMessage(MessageInputDto messageInputDto, Long chatId, Long senderId);

}
