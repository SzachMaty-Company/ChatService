package pl.szachmaty.jwt;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Map;

public class JwtTest {

    private final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9lbWFpbGFkZHJlc3MiOiJiYXJ0b3N6LnNvY2tpQGdtYWlsLmNvbSIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWUiOiJiYXJ0b3N6IHNvY2tpIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvbmFtZWlkZW50aWZpZXIiOiIxMTIxOTQ2OTM0NTgwMDM5NDAwMjMiLCJleHAiOjE3MDg4MDMzNTcsImlzcyI6IlN6YWNobWF0eV9BdXRoZW50aWNhdGlvbl9TZXJ2aWNlIiwiYXVkIjoiU3phY2htYXR5X0Zyb250ZW5kIn0.km4M91J8_r0WiV93adbIkFIYDB9eNN7kEXelzCBRONc";

    @Test
    void testConvertStringTokenToJwt() {
        try {
            JWT parsedJWT = JWTParser.parse(TOKEN);
            Map<String, Object> claims = parsedJWT.getJWTClaimsSet().getClaims();
            Map<String, Object> headers = parsedJWT.getHeader().toJSONObject();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
