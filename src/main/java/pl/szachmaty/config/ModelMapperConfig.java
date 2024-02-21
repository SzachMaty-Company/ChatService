package pl.szachmaty.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.szachmaty.model.dto.ChatResponseDto;
import pl.szachmaty.model.entity.Chat;
import pl.szachmaty.model.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public TypeMap<Chat, ChatResponseDto> chatResponseDtoTypeMap() {
        Converter<Set<User>, Set<Long>> userToUserIdConverter = (users) -> users.getSource().stream().map(User::getId).collect(Collectors.toSet());

        TypeMap<Chat, ChatResponseDto> typeMap = modelMapper().createTypeMap(Chat.class, ChatResponseDto.class);
        typeMap.addMappings(mapper -> mapper.using(userToUserIdConverter).map(Chat::getChatMembers, ChatResponseDto::setChatMembers));

        return typeMap;
    }

}
