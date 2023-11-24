package pl.szachmaty.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class AppConfig {

    final List<String> corsAllowedMethods = List.of(HttpMethod.GET.name(), HttpMethod.POST.name());
    final List<String> corsAllowedOriginPatterns = List.of("*");

    @Bean
    CorsConfiguration corsConfiguration() {
        var config = new CorsConfiguration();
        config.setAllowedMethods(corsAllowedMethods);
        config.setAllowedOriginPatterns(corsAllowedOriginPatterns);

        return config;
    }

}
