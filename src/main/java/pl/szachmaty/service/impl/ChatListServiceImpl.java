package pl.szachmaty.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import pl.szachmaty.model.dto.ChatListItem;
import pl.szachmaty.model.dto.ChatListItemDto;
import pl.szachmaty.model.dto.ChatMemberDto;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.model.repository.ChatRepository;
import pl.szachmaty.service.ChatListService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatListServiceImpl implements ChatListService {

    private final ChatRepository chatRepository;
    private final ModelMapper mapper;

    @Override
    public Slice<ChatListItem> getUserChats(String userId, Pageable pageable) {
        var chatListItems = chatRepository.getUserChats(userId, pageable);
        return chatListItems;
//        var chats = chatListItems.getContent()
//                .stream()
//                .map(ch -> mapper.map(ch, ChatListItemDto.class))
//                .toList();
//
//        chats.forEach(ch -> {
//            var chat = chatRepository.findChatFetchMembers(ch.getChatId())
//                    .orElse(new Chat());
//
//            if (chat.getChatMembers() == null) {
//                ch.setChatMembers(Set.of());
//                return;
//            }
//
//            var chatMembers = chat.getChatMembers()
//                    .stream()
//                    .map(m -> {
//                        var dto = new ChatMemberDto();
//                        dto.setUserId(m.getUserId().getId());
//                        dto.setUsername(m.getUsername());
//                        return dto;
//                    })
//                    .collect(Collectors.toSet());
//
//            ch.setChatMembers(chatMembers);
//        });
//
//        return chats;
    }

}
