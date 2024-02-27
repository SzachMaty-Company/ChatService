package pl.szachmaty.security.filter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessagingException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.model.repository.UserRepository;
import pl.szachmaty.model.value.UserId;
import pl.szachmaty.security.UserAuthenticationToken;

import java.io.IOException;
import java.text.ParseException;

public class JwtFilter extends OncePerRequestFilter {

    private static final String USER_ID_CLAIM = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier";
    private static final String USERNAME_CLAIM = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name";
    private static final RequestMatcher EXCLUDE_MATCHER = new AntPathRequestMatcher("/registerws");
    private final UserRepository userRepository;

    @Value("${com.szachmaty.jwt-shared}")
    private String sharedKey;

    public JwtFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (EXCLUDE_MATCHER.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String rawToken = request.getHeader("Authorization");
        if (rawToken == null) {
            throw new AccessDeniedException("Missing Jwt Token");
        }

        rawToken = rawToken.replace("Bearer ", "");

        String userId = null;
        String username = null;
        boolean isSignatureValid = false;

        try {
            SignedJWT jwt = (SignedJWT) JWTParser.parse(rawToken);
            JWSVerifier verifier = new MACVerifier(sharedKey.getBytes());
            isSignatureValid = jwt.verify(verifier);

            userId = (String) jwt.getJWTClaimsSet().getClaim(USER_ID_CLAIM);
            username = (String) jwt.getJWTClaimsSet().getClaim(USERNAME_CLAIM);
        } catch (ParseException e) {
            throw new MessagingException(e.getMessage());
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        if (!isSignatureValid) {
            throw new RuntimeException("Invalid JWT signature");
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

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
