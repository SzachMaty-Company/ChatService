package pl.szachmaty.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.szachmaty.model.repository.UserRepository;
import pl.szachmaty.security.CustomPreAuthenticatedAuthenticationProvider;
import pl.szachmaty.security.filter.UserJwtAuthenticationFilter;

import java.util.List;

@EnableWebSecurity(debug = true)
@EnableMethodSecurity
@Configuration
public class HttpSecurityConfig {

    @Autowired
    final UserRepository userRepository;

    public HttpSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    @Order(50)
    SecurityFilterChain httpSecurity1(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/registerws", "/chat/**", "/user/**")
                .cors(x -> x.configurationSource(corsConfigurationSource()))
                .csrf(x -> x.disable())
                .httpBasic(x -> x.disable())
                .formLogin(x -> x.disable())
                .addFilterAfter(jwtFilter(), LogoutFilter.class)
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(x -> x
                        .requestMatchers("/registerws").permitAll()
//                        .requestMatchers(HttpMethod.OPTIONS, "/user/chats").permitAll()
//                        .requestMatchers(HttpMethod.OPTIONS, "/chats").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    @Order(49)
    SecurityFilterChain httpSecurity2(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/internal/**")
                .csrf(x -> x.disable())
                .httpBasic(x -> x.disable())
                .formLogin(x -> x.disable())
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(x -> x.anyRequest().permitAll())
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager() {
        return new ProviderManager(customPreAuthenticatedAuthenticationProvider());
    }

    @Bean
    AuthenticationProvider customPreAuthenticatedAuthenticationProvider() {
        return new CustomPreAuthenticatedAuthenticationProvider(userRepository);
    }

    @Bean
    UserJwtAuthenticationFilter jwtFilter() {
        var filter = new UserJwtAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var conf = new CorsConfiguration();
        conf.setAllowCredentials(true);
        conf.setAllowedOriginPatterns(List.of("*"));
        conf.setAllowedHeaders(List.of("Authorization"));
//        conf.addAllowedOrigin("*");
        conf.setAllowedMethods(List.of("OPTIONS", "GET", "POST", "DELETE", "PUT", "PATCH"));
//        conf.addAllowedHeader("*");
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", conf);
        return source;
    }

}
