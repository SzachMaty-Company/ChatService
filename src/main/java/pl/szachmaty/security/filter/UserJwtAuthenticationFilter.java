package pl.szachmaty.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

public class UserJwtAuthenticationFilter extends RequestHeaderAuthenticationFilter {

    private String authenticationHeader = "Authorization";

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String rawToken = request.getHeader(authenticationHeader);

        return rawToken;
    }

}
