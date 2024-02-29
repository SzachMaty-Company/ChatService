package pl.szachmaty.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import pl.szachmaty.security.CustomStompErrorHandler;
import pl.szachmaty.security.annotation.StompAuthenticationPrincipalArgumentResolver;
import pl.szachmaty.security.interceptor.AuthenticationChannelInterceptor;
import pl.szachmaty.security.interceptor.MaxSessionChannelInterceptor;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final AuthenticationManager authenticationManager;
    private final SimpUserRegistry simpUserRegistry;

    @Value("${ws.max-sessions:2}")
    private int maxSessions;

    public WebSocketConfig(AuthenticationManager authenticationManager, @Lazy SimpUserRegistry simpUserRegistry) {
        this.authenticationManager = authenticationManager;
        this.simpUserRegistry = simpUserRegistry;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.setErrorHandler(new CustomStompErrorHandler());
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
                new AuthenticationChannelInterceptor(authenticationManager),
                new MaxSessionChannelInterceptor(maxSessions, simpUserRegistry)
        );
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.addAll(List.of(new StompAuthenticationPrincipalArgumentResolver()));
    }

}
