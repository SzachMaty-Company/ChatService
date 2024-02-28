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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.model.repository.UserRepository;
import pl.szachmaty.model.value.UserId;
import pl.szachmaty.security.UserJwtAuthenticationToken;

import java.text.ParseException;
import java.util.List;

public class AuthenticationChannelInterceptor implements ChannelInterceptor {

    private static final String TOKEN_HEADER = "token";
    private static final String USER_ID_CLAIM = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier";
    private static final String USERNAME_CLAIM = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name";
    private final AuthenticationManager authenticationManager;

    public AuthenticationChannelInterceptor(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (!StompCommand.CONNECT.equals(accessor.getCommand())) {
            return message;
        }

        String nativeToken = accessor.getFirstNativeHeader(TOKEN_HEADER);

        if (nativeToken == null) {
            throw new MessagingException("Missing authentication token header");
        }

        String rawToken = nativeToken.replace("Bearer ", "");
        Authentication token = new PreAuthenticatedAuthenticationToken(rawToken, "N/A");
        Authentication authenticated = authenticationManager.authenticate(token);

//        won't work because SecurityContextHolder uses threadlocal and websockets are async

//        SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
//        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
//        context.setAuthentication(authenticated);
//        securityContextHolderStrategy.setContext(context);

        accessor.setUser(authenticated);

        return message;
    }

}
