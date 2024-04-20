package pl.szachmaty.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class ChatCreationRequest {

    Set<ChatMemberDto> chatMembers;

}
