package pl.szachmaty.model.dto;

import lombok.Getter;
import lombok.Setter;
import pl.szachmaty.model.entity.User;

@Getter
@Setter
public class ChatParticipantDto {

    private String participantId;
    private String username;

    public ChatParticipantDto(String participantId, String username) {
        this.participantId = participantId;
        this.username = username;
    }

    public static ChatParticipantDto fromUser(User user) {
        return new ChatParticipantDto(user.getUserId().getId(), user.getUsername());
    }

}
