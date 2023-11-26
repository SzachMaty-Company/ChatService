package pl.szachmaty.model.dto;

import lombok.Getter;
import lombok.Setter;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.model.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ChatOutputDto {

    private Long id;
    private Set<Long> chatMembers;

    public static ChatOutputDto convert(Chat chat) {
        var dto = new ChatOutputDto();
        dto.setId(chat.getId());
        dto.setChatMembers(chat.getChatMembers().stream().map(User::getId).collect(Collectors.toSet()));

        return dto;
    }

}
