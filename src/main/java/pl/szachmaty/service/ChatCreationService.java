package pl.szachmaty.service;

import java.util.Set;

public interface ChatCreationService {

    Long createChat(Set<Long> chatMembersIds);

}
