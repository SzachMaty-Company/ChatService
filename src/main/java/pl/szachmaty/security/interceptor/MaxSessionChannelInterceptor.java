package pl.szachmaty.security.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import pl.szachmaty.exception.AppMessagingException;

public class MaxSessionChannelInterceptor implements ChannelInterceptor {

    private static final int DEFAULT_MAX_SESSIONS = 2;
    private int maxSessions;
    private SimpUserRegistry simpUserRegistry;

    public MaxSessionChannelInterceptor(SimpUserRegistry simpUserRegistry) {
        this(DEFAULT_MAX_SESSIONS, simpUserRegistry);
    }

    public MaxSessionChannelInterceptor(int maxSessions, SimpUserRegistry simpUserRegistry) {
        this.maxSessions = maxSessions;
        this.simpUserRegistry = simpUserRegistry;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (!StompCommand.CONNECT.equals(accessor.getCommand())) {
            return message;
        }

        SimpUser simpUser = simpUserRegistry.getUser(accessor.getUser().getName());

        if (simpUser == null) {
            return message;
        }

        int userSessionsCount = simpUser.getSessions().size();

        if (userSessionsCount >= maxSessions) {
            throw new AppMessagingException("Maximum sessions for user (" + simpUser.getName() + ") exceeded (" + userSessionsCount + " >= " + maxSessions + ")");
        }

        return message;
    }

}
