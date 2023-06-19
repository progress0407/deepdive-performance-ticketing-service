package numble.deepdive.performanceticketingservice.global.config;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component
public class JwtManager {

    private final static SignatureAlgorithm ENCODING_ALGORITHM = HS512;

    private final SecretKey secretKey;

    private final Long expirationDurationTime;

    public JwtManager(SecretKey secretKey, @Value("${jwt.expiration-duration-time}") Long expirationDurationTime) {
        this.secretKey = secretKey;
        this.expirationDurationTime = expirationDurationTime;
    }

    public String createAccessToken(String tokenSubject) {

        return Jwts.builder()
                .signWith(secretKey, ENCODING_ALGORITHM)
                .setSubject(tokenSubject)
                .setIssuedAt(new Date())
                .setExpiration(createExpirationDateTime())
                .compact();
    }

    public boolean isValidToken(String accessToken) {

        try {
            tryParseJwt(accessToken);
        } catch (IllegalArgumentException |
                 SignatureException |
                 MalformedJwtException |
                 ExpiredJwtException |
                 UnsupportedJwtException e) {
            return false;
        }
        return true;
    }

    public String parse(String accessToken) {

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
    }

    private void tryParseJwt(String accessToken) {

        Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken);
    }

    private Date createExpirationDateTime() {

        return new Date(System.currentTimeMillis() + expirationDurationTime);
    }
}
