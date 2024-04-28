package pl.szachmaty.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.szachmaty.model.value.UserId;

@Getter
@Setter
public class ChatMemberDto {

    private String userId;
    private String username;

    @Override
    public String toString() {
        return "ChatMemberDto{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

}
