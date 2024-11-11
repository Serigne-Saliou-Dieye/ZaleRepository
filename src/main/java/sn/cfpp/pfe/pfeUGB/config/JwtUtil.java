// package sn.cfpp.pfe.pfeUGB.config;

// import java.util.Date;
// import java.util.function.Function;
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;


// public class JwtUtil {

//     private String SECRET_KEY = "crossguild14"; // Remplacez par une clé secrète forte

//     // Génération du token
//     @SuppressWarnings("deprecation")
//     public String generateToken(String username) {
//         return Jwts.builder()
//                 .setSubject(username)
//                 .setIssuedAt(new Date(System.currentTimeMillis()))
//                 .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Ex. 10 heures
//                 .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                 .compact();
//     }

//     // Extraction du nom d'utilisateur du token
//     public String extractUsername(String token) {
//         return extractClaim(token, Claims::getSubject);
//     }

//     // Validation du token
//     public Boolean validateToken(String token, String username) {
//         final String extractedUsername = extractUsername(token);
//         return (extractedUsername.equals(username) && !isTokenExpired(token));
//     }

//     // Extraction des informations d'expiration
//     private Boolean isTokenExpired(String token) {
//         return extractExpiration(token).before(new Date());
//     }

//     private Date extractExpiration(String token) {
//         return extractClaim(token, Claims::getExpiration);
//     }

//     // Extraction d'une information (claim) générique
//     public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//         @SuppressWarnings("deprecation")
//         final Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//         return claimsResolver.apply(claims);
//     }

// }
