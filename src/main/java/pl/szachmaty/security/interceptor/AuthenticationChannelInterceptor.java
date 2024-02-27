package pl.szachmaty.security.interceptor;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.model.repository.UserRepository;
import pl.szachmaty.model.value.UserId;
import pl.szachmaty.security.UserAuthenticationToken;

import java.text.ParseException;
import java.util.List;

public class AuthenticationChannelInterceptor implements ChannelInterceptor {

    private static final String TOKEN_HEADER = "token";
    private static final String USER_ID_CLAIM = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier";
    private static final String USERNAME_CLAIM = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name";
    private final UserRepository userRepository;

    public AuthenticationChannelInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (!StompCommand.CONNECT.equals(accessor.getCommand())) {
            return message;
        }

        List<String> nativeToken = accessor.getNativeHeader(TOKEN_HEADER);

        if (nativeToken == null) {
            throw new MessagingException("Missing authentication token header");
        }

        if (nativeToken.size() != 1) {
            throw new MessagingException("Multiple token headers found, don't know which one to choose");
        }

        String userId = null;
        String username = null;
        try {
            // TODO add signature validation
            JWT jwt = JWTParser.parse(nativeToken.get(0));

            userId = (String) jwt.getJWTClaimsSet().getClaim(USER_ID_CLAIM);
            username = (String) jwt.getJWTClaimsSet().getClaim(USERNAME_CLAIM);
        } catch (ParseException e) {
            throw new MessagingException(e.getMessage());
        }

        User principal = userRepository.findUserByUserId(userId);
        if (principal == null) {
            principal = userRepository.save(
                    User.builder()
                            .userId(new UserId(userId))
                            .username(username)
                            .build()
            );
        }

        Authentication authentication = new UserAuthenticationToken(principal);
        authentication.setAuthenticated(true);
        accessor.setUser(authentication);

        return message;
    }

}
