package pl.szachmaty.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ChatCreationRequest {

    private Set<String> chatMembersIds;

}
