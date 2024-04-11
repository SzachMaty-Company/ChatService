package pl.szachmaty.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameInviteDto {

    private String inviteSenderId;
    private String inviteReceiverId;
    private String gameCode;

}
