package pl.szachmaty.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

@Slf4j
public class UserJwtAuthenticationFilter extends RequestHeaderAuthenticationFilter {

    private String authenticationHeader = "Authorization";

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String rawToken = request.getHeader(authenticationHeader);

        log.error("TOKEN: " + rawToken);

        return rawToken;
    }

}
