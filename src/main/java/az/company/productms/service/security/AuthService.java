package az.company.productms.service.security;

import az.company.productms.error.ErrorsFinal;
import az.company.productms.exception.ApplicationException;
import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class AuthService {

    private final String jwtSecret;

    public AuthService(@Value("${jwt.secret}") String secret) {
        this.jwtSecret = secret;
    }

    public boolean validateToken(String token) throws Exception {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
            throw new ApplicationException(ErrorsFinal.EXPIRED_JWT_ERROR);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
            throw new ApplicationException(ErrorsFinal.UNSUPPORTED_JWT_ERROR);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
            throw new ApplicationException(ErrorsFinal.MALFORMED_JWT_ERROR);
        } catch (SignatureException e) {
            throw new ApplicationException(ErrorsFinal.SIGNATURE_JWT_ERROR);
        } catch (JwtException e) {
            log.error("invalid token", e);
            throw new ApplicationException(ErrorsFinal.INVALID_TOKEN);
        }
//        return false;
    }

    public Claims getClaims(@NonNull String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

//    public JwtAuthentication getAuthentication() {
//        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
//    }

}
