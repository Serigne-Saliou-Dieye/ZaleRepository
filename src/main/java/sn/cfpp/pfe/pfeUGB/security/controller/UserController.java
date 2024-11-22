package sn.cfpp.pfe.pfeUGB.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import sn.cfpp.pfe.pfeUGB.security.config.JwtService;
import sn.cfpp.pfe.pfeUGB.security.entite.AuthRequest;
import sn.cfpp.pfe.pfeUGB.security.entite.Roles;
import sn.cfpp.pfe.pfeUGB.security.entite.UserInfoService;
import sn.cfpp.pfe.pfeUGB.security.entite.UserInfos;

import java.util.List;
import java.util.Optional;

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

    // Ajouter un nouvel utilisateur
    @PostMapping("/addNewUser")
    // public String addNewUser(@RequestBody UserInfo userInfo) {
    //     return userInfoService.addUser(userInfo);
    // }
    public String addNewUser(@RequestBody UserInfos userInfo) {
        try {
            return userInfoService.addUser(userInfo);
        } catch (Exception e) {
            e.printStackTrace();  // Ou utilisez Logger
            throw e;
        }
    }
    

    @PostMapping("/generateToken")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        // Check if the authentication is successful
        if (authentication.isAuthenticated()) {
            // Get the authenticated user's email
            String email = authRequest.getEmail();

            // Check if the user exists in the database
            Optional<UserInfos> userOptional = userInfoService.findByEmail(email);
            if (userOptional.isPresent()) {
                UserInfos user = userOptional.get();

                // Check if the user's role is ADMIN
                if (user.getRoles() == Roles.ADMIN) { // Si c'est un simple enum
                    // Generate the JWT token
                    String token = jwtService.generateToken(email);
                    return ResponseEntity.ok(token);
                } else {
                    throw new UsernameNotFoundException("Invalid user request! User does not have the ADMIN role.");
                }
            } else {
                throw new UsernameNotFoundException("User not found in the database.");
            }
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    // Obtenir tous les utilisateurs
    @GetMapping
    // @PreAuthorize("hasRole('ADMIN')")
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
    // @GetMapping("/users/search")
    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    // public ResponseEntity<List<UserInfos>> searchUsers(@RequestParam("name") String name) {
    //     List<UserInfos> users = userInfoService.searchByName(name);
    //     return ResponseEntity.ok(users);
    // }







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
