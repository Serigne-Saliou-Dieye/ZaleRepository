// package sn.cfpp.pfe.pfeUGB.testUnitaire;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import org.junit.jupiter.api.Test;
// import org.springframework.security.core.userdetails.UserDetails;

// import sn.cfpp.pfe.pfeUGB.security.config.JwtService;
// import sn.cfpp.pfe.pfeUGB.security.entite.Roles;
// import sn.cfpp.pfe.pfeUGB.security.entite.UserInfoDetails;
// import sn.cfpp.pfe.pfeUGB.security.entite.UserInfos;

// public class JwtServiceTest {
//     private final JwtService jwtService = new JwtService();

//     @Test
// public void testJwtToken() {
//     // Créez un utilisateur avec le rôle approprié
//     UserInfos user = new UserInfos();
//     user.setEmail("testuser@example.com");
//     user.setUsername("test");
//     user.setPassword("1234"); // Assurez-vous que le mot de passe est encodé si nécessaire
//     user.setRoles(Roles.ROLE_ADMIN); // Assurez-vous que le rôle est correct

//     // Créez un UserInfoDetails à partir de l'utilisateur
//     UserDetails userDetails = new UserInfoDetails(user);

//     // Générer le token
//     String token = jwtService.generateToken(user.getEmail());
//     System.out.println("Token généré : " + token);

//     // Extraire le nom d'utilisateur
//     String extractedUsername = jwtService.extractUsername(token);
//     System.out.println("Nom d'utilisateur extrait : " + extractedUsername);

//     // Valider le token
//     boolean isValid = jwtService.validateToken(token, userDetails);
//     System.out.println("Token valide : " + isValid);

//     // Assertions pour vérifier le comportement
//     assertEquals(user.getEmail(), extractedUsername);
//     assertTrue(isValid);
// }

// }
