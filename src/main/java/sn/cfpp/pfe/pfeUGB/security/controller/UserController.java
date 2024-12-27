package sn.cfpp.pfe.pfeUGB.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import sn.cfpp.pfe.pfeUGB.security.config.JwtService;
import sn.cfpp.pfe.pfeUGB.security.entite.AuthRequest;
import sn.cfpp.pfe.pfeUGB.security.entite.Roles;
import sn.cfpp.pfe.pfeUGB.security.entite.UserInfoDetails;
import sn.cfpp.pfe.pfeUGB.security.entite.UserInfoService;
import sn.cfpp.pfe.pfeUGB.security.entite.UserInfos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000") // Permettre les requêtes depuis React
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome, this endpoint is not secure";
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getClientCount() {
        long count = userInfoService.countUsers();
        return ResponseEntity.ok(count);
    }

    // Ajouter un nouvel utilisateur
    @PostMapping("/addNewUser")
    public ResponseEntity<UserInfos> addNewUser(@RequestBody UserInfos userInfo) {
        return userInfoService.addUser(userInfo);
    }
    // public String addNewUser(@RequestBody UserInfos userInfo) {
    //     try {
    //         return userInfoService.addUser(userInfo);
    //     } catch (Exception e) {
    //         e.printStackTrace();  // Ou utilisez Logger
    //         throw e;
    //     }
    // }
    


    @PostMapping("/generateToken")
    public ResponseEntity<Map<String, Object>> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            // Authentification de l'utilisateur
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            // Vérification si l'utilisateur est authentifié
            if (authentication.isAuthenticated()) {
                // Récupérer les détails de l'utilisateur
                UserInfoDetails userDetails = (UserInfoDetails) authentication.getPrincipal(); // Assurez-vous que vous avez une classe UserInfoDetails
                Long userId = userDetails.getId(); // Récupérer l'ID de l'utilisateur

                // Utiliser `userDetails` pour générer le token
                String token = jwtService.generateToken(userDetails);
                System.out.println("Token généré : " + token);
            
                // Construire la réponse
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("username", userDetails.getUsername());
                response.put("id", userId); // Ajoutez l'ID de l'utilisateur
                response.put("isEnabled", userDetails.isEnabled()); // Assurez-vous que vous avez accès à cette méthode
                // Récupérer le rôle directement
                response.put("role", userDetails.getRole()); // Ajouter le rôle à la réponse


                return ResponseEntity.ok(response);
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }

    // @PostMapping("/generateToken")
    // public ResponseEntity<Map<String, String>> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
    //     // Authentification de l'utilisateur
    //     Authentication authentication = authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
    //     );

    //     if (!authentication.isAuthenticated()) {
    //         throw new UsernameNotFoundException("Invalid user request!");
    //     }

    //     // Recherche de l'utilisateur par email
    //     Optional<UserInfos> userOptional = userInfoService.findByEmail(authRequest.getEmail());
    //     if (userOptional.isEmpty()) {
    //         throw new UsernameNotFoundException("Utilisateur non trouvé dans la base.");
    //     }

    //     UserInfos user = userOptional.get();

    //     // Vérification si l'utilisateur est activé
    //     if (!user.isEnabled()) {
    //         throw new UsernameNotFoundException("Utilisateur désactivé. Veuillez contacter l'administrateur.");
    //     }

    //     // Vérification du rôle de l'utilisateur
    //     if (user.getRoles() != Roles.ROLE_ADMIN){
    //         throw new UsernameNotFoundException("Seuls les administrateurs et les clients sont autorisés .");
    //     }

    //     // Génération du token JWT
    //     String token = jwtService.generateToken(authRequest.getEmail());

    //     // Réponse
    //     Map<String, String> response = new HashMap<>();
    //     response.put("token", token);
    //     response.put("username", user.getUsername());
    //     response.put("isEnabled", String.valueOf(user.isEnabled()));


    //     return ResponseEntity.ok(response);
    // }



    // Obtenir tous les utilisateurs
    @GetMapping
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserInfos>> getAllUsers() {
        List<UserInfos> users = userInfoService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Obtenir un utilisateur par son ID
    @GetMapping("/users/{id}")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserInfos> getUserById(@PathVariable Long id) {
        Optional<UserInfos> user = userInfoService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Mettre à jour un utilisateur
    @PutMapping("/users/{id}")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserInfos> updateUser(@PathVariable Long id, @RequestBody UserInfos updatedUser) {
        Optional<UserInfos> user = userInfoService.getUserById(id);
        if (user.isPresent()) {
            UserInfos savedUser = userInfoService.updateUser(id, updatedUser);
            return ResponseEntity.ok(savedUser);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Supprimer un utilisateur
    @DeleteMapping("/users/{id}")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userInfoService.deleteUser(id);
            return ResponseEntity.ok("Utilisateur supprimé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
        }
    }

    // Rechercher un utilisateur par son nom
    // @GetMapping("/roles/{roles}")
    // public List<UserInfos> searchRoles(@RequestParam("roles") Roles roles) {
    //     // List<UserInfos> users = userInfoService.searchByRole(role);
    //     // return ResponseEntity.ok(users);
    //     return userInfoService.searchByRole(roles);
    // }




    // Rechercher un utilisateur par son nom
    @GetMapping("/users/search")
    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Optional<UserInfos> searchUsers(@RequestParam("username") String username) {
        // List<UserInfos> users = userInfoService.searchByName(username);
        // return ResponseEntity.ok(users);
        return userInfoService.searchByName(username);
    }

    // Activation d'un compte ou desactivation
    @PatchMapping("/{id}/enable")
    public ResponseEntity<String> toggleUserAccount(@PathVariable Long id, @RequestParam boolean isEnabled) {
        userInfoService.toggleUserAccount(id, isEnabled);
        return ResponseEntity.ok(isEnabled ? "Compte activé" : "Compte désactivé");
    }







    // ------------------------------------------------------------------------------------------------
     // Endpoint d'inscription
    //  @PostMapping("/register")
    //  public ResponseEntity<String> registerUser(@RequestBody UserInfos userInfos) {
    //      try {
    //          // Ajouter l'utilisateur avec cryptage du mot de passe
    //          String response = userInfoService.addUser(userInfos);
    //          return ResponseEntity.status(HttpStatus.CREATED).body("Utilisateur créé avec succès !");
    //      } catch (Exception e) {
    //          e.printStackTrace(); // Journaux pour le débogage
    //          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de l'inscription.");
    //      }
    //  }
 
    //  // Endpoint de connexion
    //  @PostMapping("/login")
    //  public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
    //      try {
    //          // Authentifier l'utilisateur avec email et mot de passe
    //          Authentication authentication = authenticationManager.authenticate(
    //              new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
    //          );
 
    //          if (authentication.isAuthenticated()) {
    //              // Générer le jeton JWT
    //              String token = jwtService.generateToken(authRequest.getEmail());
    //              return ResponseEntity.ok().body(new AuthResponse(token));
    //          } else {
    //              return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de l'authentification !");
    //          }
    //      } catch (Exception e) {
    //          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect.");
    //      }
    //  }
 
}
