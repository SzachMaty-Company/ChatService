package pl.szachmaty.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GameInviteDto {

    private String inviteSenderId;
    private String inviteReceiverId;
    private String gameCode;

}
