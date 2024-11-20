package sn.cfpp.pfe.pfeUGB.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import sn.cfpp.pfe.pfeUGB.security.config.JwtService;
import sn.cfpp.pfe.pfeUGB.security.entite.AuthRequest;
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
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        // Authentifier l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
   
        // Vérifier si l'authentification a réussi
        if (authentication.isAuthenticated()) {
            // Générer le jeton JWT
            String token = jwtService.generateToken(authRequest.getEmail());
            return ResponseEntity.ok(token);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
    // Obtenir tous les utilisateurs
    @GetMapping
    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserInfos>> getAllUsers() {
        List<UserInfos> users = userInfoService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Obtenir un utilisateur par son ID
    @GetMapping("/users/{id}")
    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserInfos> getUserById(@PathVariable Long id) {
        Optional<UserInfos> user = userInfoService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Mettre à jour un utilisateur
    @PutMapping("/users/{id}")
    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
}
