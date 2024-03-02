package pl.szachmaty.service;

import pl.szachmaty.model.dto.ChatCreationRequest;
import pl.szachmaty.model.entity.Chat;

public interface ChatCreationService {

    Chat createChat(ChatCreationRequest dto);

}
