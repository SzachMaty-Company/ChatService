package pl.szachmaty.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ChatCreationRequestDto {

    private Set<Long> chatMembersIds;

}
