package pl.szachmaty.model.dto;

import lombok.Getter;
import lombok.Setter;
import pl.szachmaty.model.value.UserId;

@Getter
@Setter
public class ChatMemberDto {

    private String userId;
    private String username;

}
