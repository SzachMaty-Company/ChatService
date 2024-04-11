package pl.szachmaty.service;

import pl.szachmaty.model.dto.ChatMemberDto;
import pl.szachmaty.model.entity.Chat;

import java.util.Set;

public interface ChatCreationService {

    Chat createChat(Set<ChatMemberDto> chatMembers);

}
