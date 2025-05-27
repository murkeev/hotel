package murkeev.config;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JWTHelper {
    @Value("${jwt.expiration}")
    public static long EXPIRATION_TIME;

    @Value("${jwt.secret}")
    public static String SECRET_KEY;

    public static String createToken(String email) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public static boolean isTokenCorrect(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public static String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(JWTHelper.SECRET_KEY.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token).getBody().getSubject();
    }
}
