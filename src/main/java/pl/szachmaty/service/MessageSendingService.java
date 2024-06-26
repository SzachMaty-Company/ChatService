package pl.szachmaty.service;

import pl.szachmaty.model.dto.GameInviteDto;
import pl.szachmaty.model.dto.Message;
import pl.szachmaty.model.entity.User;

public interface MessageSendingService {

    void sendMessage(Message message, User user);

    void sendGameInvite(GameInviteDto gameInviteDto);

}
