package pl.szachmaty.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import pl.szachmaty.model.entity.User;
import pl.szachmaty.model.repository.UserRepository;
import pl.szachmaty.model.value.UserId;

import java.text.ParseException;

public class CustomPreAuthenticatedAuthenticationProvider implements AuthenticationProvider {

    private static final String USER_ID_CLAIM = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier";
    private static final String USERNAME_CLAIM = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name";
    private final UserRepository userRepository;
    @Value("${com.szachmaty.jwt-shared}")
    private String sharedKey;

    public CustomPreAuthenticatedAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String rawToken = ((String) authentication.getPrincipal()).replace("Bearer ", "");

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

            throw new BadCredentialsException("Invalid JWT");
        } catch (JOSEException e) {
            throw new BadCredentialsException(e.getMessage());
        }

        if (!isSignatureValid) {
            throw new BadCredentialsException("Invalid JWT signature");
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

        Authentication userJwtAuthenticationToken = new UserJwtAuthenticationToken(principal);
        userJwtAuthenticationToken.setAuthenticated(true);

        return userJwtAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
