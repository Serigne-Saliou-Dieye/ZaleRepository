package sn.cfpp.pfe.pfeUGB.security.entite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.transaction.Transactional;
import sn.cfpp.pfe.pfeUGB.model.Client;
import sn.cfpp.pfe.pfeUGB.model.Livreur;
import sn.cfpp.pfe.pfeUGB.repositories.ClientRepository;
import sn.cfpp.pfe.pfeUGB.repositories.LivreurRepository;
import sn.cfpp.pfe.pfeUGB.security.cottroller.ImageController;
import sn.cfpp.pfe.pfeUGB.security.repository.UserInfoRepository;
import sn.cfpp.pfe.pfeUGB.websockets.NotificationService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final LivreurRepository livreurRepository;
    private final ImageController imageController;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository,
                           PasswordEncoder passwordEncoder,
                           ClientRepository clientRepository,
                           LivreurRepository livreurRepository,
                           ImageController imageController) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.clientRepository = clientRepository;
        this.livreurRepository = livreurRepository;
        this.imageController = imageController;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Recherche de l'utilisateur par username
        UserInfos userInfo = userInfoRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + username));

        // Vérification si l'utilisateur est activé
        if (!userInfo.isEnabled()) {
            throw new UsernameNotFoundException("Utilisateur désactivé : " + username);
        }

        // Retourner une instance de UserInfoDetails
        return new UserInfoDetails(userInfo); // Assurez-vous que vous retournez votre classe personnalisée
    }

    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     // Recherche de l'utilisateur par username
    //     UserInfos userInfo = userInfoRepository.findByEmail(username)
    //             .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + username));

    //     // Vérification si l'utilisateur est activé
    //     if (!userInfo.isEnabled()) {
    //         throw new UsernameNotFoundException("Utilisateur désactivé : " + username);
    //     }

    //     // Conversion des rôles/permissions
    //     List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userInfo.getRoles().name()));

    //     // Retour d'un objet UserDetails
    //     return new org.springframework.security.core.userdetails.User(
    //             userInfo.getUsername(),
    //             userInfo.getPassword(),
    //             userInfo.isEnabled(),
    //             true, // accountNonExpired
    //             true, // credentialsNonExpired
    //             true, // accountNonLocked
    //             authorities
    //     );
    // }

    public ResponseEntity<UserInfos> addUser(UserInfos userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        UserInfos savedUser = userInfoRepository.save(userInfo);
        notificationService.sendNotification("Nouveau utilisateur ajouté");

        return ResponseEntity.ok(savedUser) ;
    }

    @Transactional
    public UserInfos saveUserInfo(UserInfos userInfo, MultipartFile imageFile) throws IOException {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

        if (userInfo.getRoles() == null || "ADMIN".equals(userInfo.getRoles())) {
            throw new IllegalArgumentException("Le rôle ADMIN ne doit pas être associé aux entités Client ou Livreur.");
        }

        // Enregistrer d'abord l'utilisateur sans l'association Client ou Livreur
        UserInfos savedUserInfo = userInfoRepository.save(userInfo);

        // Associer l'utilisateur au rôle et enregistrer les entités associées
        if ("CLIENT".equals(userInfo.getRoles())) {
            Client client = new Client();
            client.setUserClient(savedUserInfo);
            savedUserInfo.setClient(client);

            String imagePath = imageController.uploadClientImage(client.getIdCl(), imageFile);
            client.setImageCl(imagePath);

            clientRepository.save(client);
        } else if ("LIVREUR".equals(userInfo.getRoles())) {
            Livreur livreur = new Livreur();
            livreur.setUserLivreur(savedUserInfo);
            savedUserInfo.setLivreur(livreur);

            String imagePath = imageController.uploadLivreurImage(livreur.getIdLivreur(), imageFile);
            livreur.setImageLiv(imagePath);

            livreurRepository.save(livreur);
        }

        return savedUserInfo;
    }

    // Récupérer un utilisateur par son ID
    public Optional<UserInfos> getUserById(Long id) {
        return userInfoRepository.findById(id);
    }

    // Mettre à jour un utilisateur
    public UserInfos updateUser(Long id, UserInfos updatedUser) {
        UserInfos existingUser = getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
    
        // Conserver le mot de passe actuel s'il n'est pas modifié
        if (updatedUser.getPassword() == null || updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(existingUser.getPassword());
        } else {
            // Encoder le mot de passe s'il a été fourni
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
    
        // Mettre à jour les autres champs
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRoles(updatedUser.getRoles());
        // Ajoutez d'autres champs si nécessaire
    
        return userInfoRepository.save(existingUser);
    }

    // Supprimer un utilisateur
    public void deleteUser(Long id) {
        UserInfos user = userInfoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        userInfoRepository.delete(user);
    }

    // Obtenir tous les utilisateurs
    public List<UserInfos> getAllUsers() {
        return userInfoRepository.findAll();
    }

    public Optional<UserInfos> findByEmail(String email) {
        return userInfoRepository.findByEmail(email);
    }

    public long countUsers() {
        return userInfoRepository.count();
    }

    //Recherche par  roles
    // public List<UserInfos> searchByRole(Roles roles) {
    //     return userInfoRepository.findByRole(roles);
    // }


    // Rechercher un utilisateur par son nom
    public Optional<UserInfos> searchByName(String username) {
        return userInfoRepository.findByUsername(username);
    }

    public void toggleUserAccount(Long id, boolean isEnabled) {
        UserInfos userInfo = userInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        userInfo.setEnabled(isEnabled); // Mise à jour de l'état
        userInfoRepository.save(userInfo); // Sauvegarder dans la base
    }
}
