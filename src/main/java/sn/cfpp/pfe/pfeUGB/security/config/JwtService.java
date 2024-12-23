package sn.cfpp.pfe.pfeUGB.security.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    private static final String SECRET = "HZpHOTbEYXU1mFfYURISkXG9Dri0l2w4imsHWTb2RPI="; // Remplacez ceci par une clé sécurisée
    private static final long EXPIRATION_TIME = 1000 * 60 * 30; // 30 minutes

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities()) // Ajoutez les rôles ici
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    

    // public String generateToken(String username) {
    //     if (username == null || username.isEmpty()) {
    //         logger.error("Le nom d'utilisateur ne peut pas être nul ou vide");
    //         throw new IllegalArgumentException("Le nom d'utilisateur ne peut pas être nul ou vide");
    //     }

    //     String token = Jwts.builder()
    //             .setSubject(username)
    //             .setIssuedAt(new Date())
    //             .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
    //             .signWith(getSignKey(), SignatureAlgorithm.HS256)
    //             .compact();

    //     logger.info("Token généré pour l'utilisateur {} : {}", username, token);
    //     return token;
    // }

    public String extractUsername(String token) {
        if (token == null || token.isEmpty()) {
            logger.error("Le token ne peut pas être nul ou vide");
            throw new IllegalArgumentException("Le token ne peut pas être nul ou vide");
        }

        try {
            String username = Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            logger.info("Nom d'utilisateur extrait du token : {}", username);
            return username;
        } catch (JwtException e) {
            logger.error("Erreur lors de l'extraction du nom d'utilisateur : {}", e.getMessage());
            throw new IllegalArgumentException("Token invalide", e);
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        if (token == null || token.isEmpty()) {
            logger.error("Le token ne peut pas être nul ou vide");
            return false;
        }

        try {
            String username = extractUsername(token);
            boolean isValid = username.equals(userDetails.getUsername()) && !isTokenExpired(token);
            logger.info("Validation du token : {}", isValid);
            return isValid;
        } catch (JwtException e) {
            logger.error("Erreur lors de la validation du token : {}", e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        boolean expired = expiration.before(new Date());
        logger.info("Le token est expiré : {}", expired);
        return expired;
    }

    private Date extractExpiration(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
        } catch (JwtException e) {
            logger.error("Erreur lors de l'extraction de l'expiration du token : {}", e.getMessage());
            throw new IllegalArgumentException("Token invalide", e);
        }
    }
}