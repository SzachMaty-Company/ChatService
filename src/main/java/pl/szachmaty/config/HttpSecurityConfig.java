package pl.szachmaty.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.szachmaty.model.repository.UserRepository;
import pl.szachmaty.security.filter.JwtFilter;

import java.util.List;

@EnableWebSecurity(debug = true)
@Configuration
public class HttpSecurityConfig {

    final UserRepository userRepository;

    public HttpSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
        return http
                .cors(x -> x.disable())
                .csrf(x -> x.disable())
                .httpBasic(x -> x.disable())
                .formLogin(x -> x.disable())
                .addFilterAfter(jwtFilter(), LogoutFilter.class)
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(x -> x
                        .requestMatchers("/registerws").permitAll()
                        .anyRequest().permitAll()
                )
                .build();
    }

    @Bean
    JwtFilter jwtFilter() {
        return new JwtFilter(userRepository);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var conf = new CorsConfiguration();
        conf.setAllowCredentials(true);
        conf.addAllowedOrigin("*");
        conf.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "PATCH"));
        conf.addAllowedHeader("*");
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", conf);
        return source;
    }

}
