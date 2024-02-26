package pl.szachmaty.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import pl.szachmaty.model.repository.UserRepository;
import pl.szachmaty.security.interceptor.AuthenticationChannelInterceptor;
import pl.szachmaty.security.interceptor.MaxSessionChannelInterceptor;

@Configuration
@EnableWebSecurity
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final UserRepository userRepository;
    private final SimpUserRegistry simpUserRegistry;

    @Value("${ws.max-sessions:3}")
    private int maxSessions;

    public WebSocketConfig(UserRepository userRepository, @Lazy SimpUserRegistry simpUserRegistry) {
        this.userRepository = userRepository;
        this.simpUserRegistry = simpUserRegistry;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/registerws")
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/chat");
        config.enableSimpleBroker("/user");
        config.enableSimpleBroker("/queue");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(
                authenticationChannelInterceptor(),
                maxSessionChannelInterceptor()
        );
    }

    ChannelInterceptor authenticationChannelInterceptor() {
        return new AuthenticationChannelInterceptor(userRepository);
    }

    ChannelInterceptor maxSessionChannelInterceptor() {
        return new MaxSessionChannelInterceptor(maxSessions, simpUserRegistry);
    }

}
