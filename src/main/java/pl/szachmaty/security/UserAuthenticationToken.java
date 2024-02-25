package pl.szachmaty.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.szachmaty.model.entity.User;

import java.util.List;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

    public static final List<? extends GrantedAuthority> DEFAULT_AUTHORITIES = List.of(
            new SimpleGrantedAuthority("USER")
    );
    private User principal;

    public UserAuthenticationToken(User principal) {
        super(DEFAULT_AUTHORITIES);
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

}
