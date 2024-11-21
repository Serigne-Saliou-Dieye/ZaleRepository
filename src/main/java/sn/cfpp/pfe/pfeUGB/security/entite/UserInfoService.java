package sn.cfpp.pfe.pfeUGB.security.entite;

import org.springframework.beans.factory.annotation.Autowired;
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
        Optional<UserInfos> userDetail = userInfoRepository.findByEmail(username);
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public String addUser(UserInfos userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "User Added Successfully";
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
        return userInfoRepository.findById(id)
            .map(user -> {
                user.setEmail(updatedUser.getEmail());
                user.setUsername(updatedUser.getUsername());
                user.setRoles(updatedUser.getRoles());
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));  // S'assurer que le mot de passe est crypté
                return userInfoRepository.save(user);
            })
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
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

    // Rechercher un utilisateur par son nom
    // public List<UserInfos> searchByName(String name) {
    //     return userInfoRepository.findByNameContainingIgnoreCase(name);
    // }
}
